/*
 * YukiHookAPI - An efficient Kotlin version of the Xposed Hook API.
 * Copyright (C) 2019-2022 HighCapable
 * https://github.com/fankes/YukiHookAPI
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is Created by fankes on 2022/2/2.
 */
@file:Suppress("MemberVisibilityCanBePrivate", "unused", "PropertyName")

package com.highcapable.yukihookapi.hook.core

import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.CauseProblemsApi
import com.highcapable.yukihookapi.hook.bean.HookClass
import com.highcapable.yukihookapi.hook.core.finder.base.MemberBaseFinder
import com.highcapable.yukihookapi.hook.core.finder.members.ConstructorFinder
import com.highcapable.yukihookapi.hook.core.finder.members.FieldFinder
import com.highcapable.yukihookapi.hook.core.finder.members.MethodFinder
import com.highcapable.yukihookapi.hook.factory.*
import com.highcapable.yukihookapi.hook.log.yLoggerE
import com.highcapable.yukihookapi.hook.log.yLoggerI
import com.highcapable.yukihookapi.hook.log.yLoggerW
import com.highcapable.yukihookapi.hook.param.HookParam
import com.highcapable.yukihookapi.hook.param.PackageParam
import com.highcapable.yukihookapi.hook.param.type.HookEntryType
import com.highcapable.yukihookapi.hook.type.java.*
import com.highcapable.yukihookapi.hook.utils.await
import com.highcapable.yukihookapi.hook.xposed.bridge.YukiHookBridge
import com.highcapable.yukihookapi.hook.xposed.bridge.factory.YukiHookHelper
import com.highcapable.yukihookapi.hook.xposed.bridge.factory.YukiHookPriority
import com.highcapable.yukihookapi.hook.xposed.bridge.factory.YukiMemberHook
import com.highcapable.yukihookapi.hook.xposed.bridge.factory.YukiMemberReplacement
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Member
import java.lang.reflect.Method

/**
 * [YukiHookAPI] 的 [Member] 核心 Hook 实现类
 *
 * 核心 API 对接 [YukiHookHelper] 实现
 * @param packageParam 需要传入 [PackageParam] 实现方法调用
 * @param hookClass 要 Hook 的 [HookClass] 实例
 */
class YukiMemberHookCreator(@PublishedApi internal val packageParam: PackageParam, @PublishedApi internal val hookClass: HookClass) {

    /** 默认 Hook 回调优先级 */
    val PRIORITY_DEFAULT = YukiHookPriority.PRIORITY_DEFAULT

    /** 延迟回调 Hook 方法结果 */
    val PRIORITY_LOWEST = YukiHookPriority.PRIORITY_LOWEST

    /** 更快回调 Hook 方法结果 */
    val PRIORITY_HIGHEST = YukiHookPriority.PRIORITY_HIGHEST

    /** Hook 操作选项内容 */
    private var hookOption = ""

    /** [hookClass] 找不到时出现的错误回调 */
    private var onHookClassNotFoundFailureCallback: ((Throwable) -> Unit)? = null

    /** 是否对当前 [YukiMemberHookCreator] 禁止执行 Hook 操作 */
    @PublishedApi
    internal var isDisableCreatorRunHook = false

    /** 设置要 Hook 的 [Method]、[Constructor] */
    @PublishedApi
    internal var preHookMembers = HashMap<String, MemberHookCreator>()

    /**
     * 得到当前被 Hook 的 [Class]
     *
     * - ❗不推荐直接使用 - 万一得不到 [Class] 对象则会无法处理异常导致崩溃
     * @return [Class]
     * @throws IllegalStateException 如果当前 [Class] 未被正确装载
     */
    val instanceClass
        get() = hookClass.instance ?: error("Cannot get hook class \"${hookClass.name}\" cause ${hookClass.throwable?.message}")

    /**
     * 注入要 Hook 的 [Method]、[Constructor]
     * @param priority Hook 优先级 - 默认 [PRIORITY_DEFAULT]
     * @param tag 可设置标签 - 在发生错误时方便进行调试
     * @param initiate 方法体
     * @return [MemberHookCreator.Result]
     */
    inline fun injectMember(priority: Int = PRIORITY_DEFAULT, tag: String = "Default", initiate: MemberHookCreator.() -> Unit) =
        MemberHookCreator(priority, tag).apply(initiate).apply { preHookMembers[toString()] = this }.build()

    /**
     * 允许 Hook 过程中的所有危险行为
     *
     * 请在 [option] 中键入 "Yes do as I say!" 代表你同意允许所有危险行为
     *
     * 你还需要在整个作用域中声明注解 [CauseProblemsApi] 以消除警告
     *
     * - ❗若你不知道允许此功能会带来何种后果 - 请勿使用
     * @param option 操作选项内容
     */
    @CauseProblemsApi
    fun useDangerousOperation(option: String) {
        hookOption = option
    }

    /**
     * Hook 执行入口
     * @return [Result]
     */
    @PublishedApi
    internal fun hook() = when {
        YukiHookBridge.hasXposedBridge.not() -> Result()
        /** 过滤 [HookEntryType.ZYGOTE] 与 [HookEntryType.PACKAGE] 或 [HookParam.isCallbackCalled] 已被执行 */
        packageParam.wrapper?.type == HookEntryType.RESOURCES && HookParam.isCallbackCalled.not() -> Result()
        preHookMembers.isEmpty() -> Result().also { yLoggerW(msg = "Hook Members is empty in [${hookClass.name}], hook aborted") }
        else -> Result().await {
            when {
                isDisableCreatorRunHook.not() && hookClass.instance != null -> runCatching {
                    hookClass.instance?.apply { checkingInternal(); checkingDangerous() }
                    it.onPrepareHook?.invoke()
                    preHookMembers.forEach { (_, m) -> m.hook() }
                }.onFailure {
                    if (onHookClassNotFoundFailureCallback == null)
                        yLoggerE(msg = "Hook initialization failed because got an Exception", e = it)
                    else onHookClassNotFoundFailureCallback?.invoke(it)
                }
                isDisableCreatorRunHook.not() && hookClass.instance == null ->
                    if (onHookClassNotFoundFailureCallback == null)
                        yLoggerE(msg = "HookClass [${hookClass.name}] not found", e = hookClass.throwable)
                    else onHookClassNotFoundFailureCallback?.invoke(hookClass.throwable ?: Throwable("[${hookClass.name}] not found"))
            }
        }
    }

    /**
     * 检查不应该被 Hook 警告范围内的 [HookClass] 对象
     * @throws UnsupportedOperationException 如果遇到警告范围内的 [HookClass] 对象
     */
    private fun Class<*>.checkingDangerous() {
        /**
         * 警告并抛出异常
         * @param name 对象名称
         * @param content 警告内容
         * @throws UnsupportedOperationException 抛出警告异常
         */
        fun throwProblem(name: String, content: String) {
            if (hookOption != "Yes do as I say!") throw UnsupportedOperationException(
                "!!!DANGEROUS!!! Hook [$name] Class is a dangerous behavior! $content\n" +
                        "The hook request was rejected, if you still want to use it, " +
                        "call \"useDangerousOperation\" and type \"Yes do as I say!\""
            )
        }
        when (hookClass.name) {
            AnyType.name -> throwProblem(
                name = "Object",
                content = "This is the parent Class of all objects, if you hook it, it may cause a lot of memory leaks"
            )
            JavaClassLoader.name -> throwProblem(
                name = "ClassLoader",
                content = "If you only want to listen to \"loadClass\", just use \"ClassLoader.fetching\" instead it"
            )
            JavaClass.name, JavaMethodClass.name, JavaFieldClass.name,
            JavaConstructorClass.name, JavaMemberClass.name -> throwProblem(
                name = "Class/Method/Field/Constructor/Member",
                content = "Those Class should not be hooked, it may cause StackOverflow errors"
            )
        }
    }

    /**
     * Hook 核心功能实现类
     *
     * 查找和处理需要 Hook 的 [Method]、[Constructor]
     * @param priority Hook 优先级
     * @param tag 当前设置的标签
     */
    inner class MemberHookCreator @PublishedApi internal constructor(private val priority: Int, internal val tag: String) {

        /** Hook 结果实例 */
        private var result: Result? = null

        /** 是否已经执行 Hook */
        private var isHooked = false

        /** [beforeHook] 回调 */
        private var beforeHookCallback: (HookParam.() -> Unit)? = null

        /** [afterHook] 回调 */
        private var afterHookCallback: (HookParam.() -> Unit)? = null

        /** [replaceAny]、[replaceUnit] 回调 */
        private var replaceHookCallback: (HookParam.() -> Any?)? = null

        /** Hook 成功时回调 */
        private var onHookedCallback: ((Member) -> Unit)? = null

        /** 重复 Hook 时回调 */
        private var onAlreadyHookedCallback: ((Member) -> Unit)? = null

        /** 找不到 [members] 出现错误回调 */
        private var onNoSuchMemberFailureCallback: ((Throwable) -> Unit)? = null

        /** Hook 过程中出现错误回调 */
        private var onConductFailureCallback: ((HookParam, Throwable) -> Unit)? = null

        /** Hook 开始时出现错误回调 */
        private var onHookingFailureCallback: ((Throwable) -> Unit)? = null

        /** 全部错误回调 */
        private var onAllFailureCallback: ((Throwable) -> Unit)? = null

        /** 发生异常时是否将异常抛出给当前 Hook APP */
        private var isOnFailureThrowToApp = false

        /** 是否为替换 Hook 模式 */
        private var isReplaceHookMode = false

        /** 是否对当前 [MemberHookCreator] 禁止执行 Hook 操作 */
        @PublishedApi
        internal var isDisableMemberRunHook = false

        /** 查找过程中发生的异常 */
        @PublishedApi
        internal var findingThrowable: Throwable? = null

        /** 标识是否已经设置了要 Hook 的 [members] */
        @PublishedApi
        internal var isHookMemberSetup = false

        /** 当前的查找实例 */
        @PublishedApi
        internal var finder: MemberBaseFinder? = null

        /** 当前被 Hook 的 [Method]、[Constructor] 实例数组 */
        private val memberUnhooks = HashSet<YukiMemberHook.Unhook>()

        /** 当前需要 Hook 的 [Method]、[Constructor] */
        internal val members = HashSet<Member>()

        /**
         * 手动指定要 Hook 的 [Method]、[Constructor]
         *
         * 你可以调用 [instanceClass] 来手动查询要 Hook 的 [Method]、[Constructor]
         *
         * - ❗不建议使用此方法设置目标需要 Hook 的 [Member] 对象 - 你可以使用 [method] 或 [constructor] 方法
         *
         * - ❗在同一个 [injectMember] 中你只能使用一次 [members]、[allMembers]、[method]、[constructor] 方法 - 否则结果会被替换
         * @param member 要指定的 [Member] 或 [Member] 数组
         * @throws IllegalStateException 如果 [member] 参数为空
         */
        fun members(vararg member: Member?) {
            if (member.isEmpty()) error("Custom Hooking Members is empty")
            members.clear()
            member.forEach { it?.also { members.add(it) } }
        }

        /**
         * 查找并 Hook [hookClass] 中指定 [name] 的全部 [Method]
         *
         * - ❗此方法已弃用 - 在之后的版本中将直接被删除
         *
         * - ❗请现在转移到 [MethodFinder] 或 [allMembers]
         * @param name 方法名称
         * @return [ArrayList]<[MethodFinder.Result.Instance]>
         */
        @Deprecated(message = "请使用新方式来实现 Hook 所有方法", ReplaceWith(expression = "method { this.name = name }.all()"))
        fun allMethods(name: String) = method { this.name = name }.all()

        /**
         * 查找并 Hook [hookClass] 中的全部 [Constructor]
         *
         * - ❗此方法已弃用 - 在之后的版本中将直接被删除
         *
         * - ❗请现在转移到 [ConstructorFinder] 或 [allMembers]
         * @return [ArrayList]<[ConstructorFinder.Result.Instance]>
         */
        @Deprecated(
            message = "请使用新方式来实现 Hook 所有构造方法",
            ReplaceWith(expression = "allMembers(MembersType.CONSTRUCTOR)", "com.highcapable.yukihookapi.hook.factory.MembersType")
        )
        fun allConstructors() = allMembers(MembersType.CONSTRUCTOR)

        /**
         * 查找并 Hook [hookClass] 中的全部 [Method]、[Constructor]
         *
         * - ❗在同一个 [injectMember] 中你只能使用一次 [members]、[allMembers]、[method]、[constructor] 方法 - 否则结果会被替换
         *
         * - ❗警告：无法准确处理每个 [Member] 的返回值和 param - 建议使用 [method] or [constructor] 对每个 [Member] 单独 Hook
         *
         * - ❗如果 [hookClass] 中没有 [Member] 可能会发生错误
         * @param type 过滤 [Member] 类型 - 默认为 [MembersType.ALL]
         */
        fun allMembers(type: MembersType = MembersType.ALL) {
            members.clear()
            if (type == MembersType.ALL || type == MembersType.CONSTRUCTOR)
                hookClass.instance?.allConstructors { _, constructor -> members.add(constructor) }
            if (type == MembersType.ALL || type == MembersType.METHOD)
                hookClass.instance?.allMethods { _, method -> members.add(method) }
            isHookMemberSetup = true
        }

        /**
         * 查找 [hookClass] 需要 Hook 的 [Method]
         *
         * - ❗在同一个 [injectMember] 中你只能使用一次 [members]、[allMembers]、[method]、[constructor] 方法 - 否则结果会被替换
         * @param initiate 方法体
         * @return [MethodFinder.Process]
         */
        inline fun method(initiate: MethodConditions) = runCatching {
            isHookMemberSetup = true
            MethodFinder(hookInstance = this, hookClass.instance).apply(initiate).apply { finder = this }.process()
        }.getOrElse {
            findingThrowable = it
            MethodFinder(hookInstance = this).denied(it)
        }

        /**
         * 查找 [hookClass] 需要 Hook 的 [Constructor]
         *
         * - ❗在同一个 [injectMember] 中你只能使用一次 [members]、[allMembers]、[method]、[constructor] 方法 - 否则结果会被替换
         * @param initiate 方法体
         * @return [ConstructorFinder.Process]
         */
        inline fun constructor(initiate: ConstructorConditions = { emptyParam() }) = runCatching {
            isHookMemberSetup = true
            ConstructorFinder(hookInstance = this, hookClass.instance).apply(initiate).apply { finder = this }.process()
        }.getOrElse {
            findingThrowable = it
            ConstructorFinder(hookInstance = this).denied(it)
        }

        /**
         * 使用当前 [hookClass] 查找并得到 [Field]
         * @param initiate 方法体
         * @return [FieldFinder.Result]
         */
        inline fun HookParam.field(initiate: FieldConditions) =
            if (hookClass.instance == null) FieldFinder(hookInstance = this@MemberHookCreator).failure(hookClass.throwable)
            else FieldFinder(hookInstance = this@MemberHookCreator, hookClass.instance).apply(initiate).build()

        /**
         * 使用当前 [hookClass] 查找并得到 [Method]
         * @param initiate 方法体
         * @return [MethodFinder.Result]
         */
        inline fun HookParam.method(initiate: MethodConditions) =
            if (hookClass.instance == null) MethodFinder(hookInstance = this@MemberHookCreator).failure(hookClass.throwable)
            else MethodFinder(hookInstance = this@MemberHookCreator, hookClass.instance).apply(initiate).build()

        /**
         * 使用当前 [hookClass] 查找并得到 [Constructor]
         * @param initiate 方法体
         * @return [ConstructorFinder.Result]
         */
        inline fun HookParam.constructor(initiate: ConstructorConditions = { emptyParam() }) =
            if (hookClass.instance == null) ConstructorFinder(hookInstance = this@MemberHookCreator).failure(hookClass.throwable)
            else ConstructorFinder(hookInstance = this@MemberHookCreator, hookClass.instance).apply(initiate).build()

        /**
         * 注入要 Hook 的 [Method]、[Constructor] (嵌套 Hook)
         * @param priority Hook 优先级 - 默认 [PRIORITY_DEFAULT]
         * @param tag 可设置标签 - 在发生错误时方便进行调试
         * @param initiate 方法体
         * @return [MemberHookCreator.Result]
         */
        inline fun HookParam.injectMember(
            priority: Int = PRIORITY_DEFAULT,
            tag: String = "InnerDefault",
            initiate: MemberHookCreator.() -> Unit
        ) = this@YukiMemberHookCreator.injectMember(priority, tag, initiate).also { this@YukiMemberHookCreator.hook() }

        /**
         * 在 [Member] 执行完成前 Hook
         *
         * - 不可与 [replaceAny]、[replaceUnit]、[replaceTo] 同时使用
         * @param initiate [HookParam] 方法体
         * @return [HookCallback]
         */
        fun beforeHook(initiate: HookParam.() -> Unit): HookCallback {
            isReplaceHookMode = false
            beforeHookCallback = initiate
            return HookCallback()
        }

        /**
         * 在 [Member] 执行完成后 Hook
         *
         * - 不可与 [replaceAny]、[replaceUnit]、[replaceTo] 同时使用
         * @param initiate [HookParam] 方法体
         * @return [HookCallback]
         */
        fun afterHook(initiate: HookParam.() -> Unit): HookCallback {
            isReplaceHookMode = false
            afterHookCallback = initiate
            return HookCallback()
        }

        /**
         * 拦截并替换此 [Member] 内容 - 给出返回值
         *
         * - 不可与 [beforeHook]、[afterHook] 同时使用
         * @param initiate [HookParam] 方法体
         */
        fun replaceAny(initiate: HookParam.() -> Any?) {
            isReplaceHookMode = true
            replaceHookCallback = initiate
        }

        /**
         * 拦截并替换此 [Member] 内容 - 没有返回值 ([Unit])
         *
         * - 不可与 [beforeHook]、[afterHook] 同时使用
         * @param initiate [HookParam] 方法体
         */
        fun replaceUnit(initiate: HookParam.() -> Unit) {
            isReplaceHookMode = true
            replaceHookCallback = initiate
        }

        /**
         * 拦截并替换 [Member] 返回值
         *
         * - 不可与 [beforeHook]、[afterHook] 同时使用
         * @param any 要替换为的返回值对象
         */
        fun replaceTo(any: Any?) {
            isReplaceHookMode = true
            replaceHookCallback = { any }
        }

        /**
         * 拦截并替换 [Member] 返回值为 true
         *
         * - ❗确保替换 [Member] 的返回对象为 [Boolean]
         *
         * - 不可与 [beforeHook]、[afterHook] 同时使用
         */
        fun replaceToTrue() {
            isReplaceHookMode = true
            replaceHookCallback = { true }
        }

        /**
         * 拦截并替换 [Member] 返回值为 false
         *
         * - ❗确保替换 [Member] 的返回对象为 [Boolean]
         *
         * - 不可与 [beforeHook]、[afterHook] 同时使用
         */
        fun replaceToFalse() {
            isReplaceHookMode = true
            replaceHookCallback = { false }
        }

        /**
         * 拦截此 [Member]
         *
         * - ❗这将会禁止此 [Member] 执行并返回 null
         *
         * - ❗注意：例如 [Int]、[Long]、[Boolean] 常量返回值的 [Member] 一旦被设置为 null 可能会造成 Hook APP 抛出异常
         *
         * - 不可与 [beforeHook]、[afterHook] 同时使用
         */
        fun intercept() {
            isReplaceHookMode = true
            replaceHookCallback = { null }
        }

        /**
         * 移除当前注入的 Hook [Method]、[Constructor] (解除 Hook)
         *
         * - ❗你只能在 Hook 回调方法中使用此功能
         * @param result 回调是否成功
         */
        fun removeSelf(result: (Boolean) -> Unit = {}) = this.result?.remove(result) ?: result(false)

        /**
         * Hook 创建入口
         * @return [Result]
         */
        @PublishedApi
        internal fun build() = Result().apply { result = this }

        /** Hook 执行入口 */
        @PublishedApi
        internal fun hook() {
            if (YukiHookBridge.hasXposedBridge.not() || isHooked || isDisableMemberRunHook) return
            isHooked = true
            finder?.printLogIfExist()
            if (hookClass.instance == null) {
                (hookClass.throwable ?: Throwable("HookClass [${hookClass.name}] not found")).also {
                    onHookingFailureCallback?.invoke(it)
                    onAllFailureCallback?.invoke(it)
                    if (isNotIgnoredHookingFailure) onHookFailureMsg(it)
                }
                return
            }
            members.takeIf { it.isNotEmpty() }?.forEach { member ->
                runCatching {
                    member.hook().also {
                        when {
                            it.first?.member == null -> error("Hook Member [$member] failed")
                            it.second -> onAlreadyHookedCallback?.invoke(it.first?.member!!)
                            else -> it.first?.also { e ->
                                memberUnhooks.add(e)
                                onHookedCallback?.invoke(e.member!!)
                            }
                        }
                    }
                }.onFailure {
                    onHookingFailureCallback?.invoke(it)
                    onAllFailureCallback?.invoke(it)
                    if (isNotIgnoredHookingFailure) onHookFailureMsg(it, member)
                }
            } ?: Throwable("Finding Error isSetUpMember [$isHookMemberSetup] [$tag]").also {
                onNoSuchMemberFailureCallback?.invoke(it)
                onHookingFailureCallback?.invoke(it)
                onAllFailureCallback?.invoke(it)
                if (isNotIgnoredNoSuchMemberFailure) yLoggerE(
                    msg = (if (isHookMemberSetup)
                        "Hooked Member with a finding error by $hookClass [$tag]"
                    else "Hooked Member cannot be non-null by $hookClass [$tag]"),
                    e = findingThrowable ?: it
                )
            }
        }

        /**
         * Hook [Method]、[Constructor]
         * @return [Pair] - ([YukiMemberHook.Unhook] or null,[Boolean] 是否已经 Hook)
         */
        private fun Member.hook(): Pair<YukiMemberHook.Unhook?, Boolean> {
            /** 定义替换 Hook 的 [HookParam] */
            val replaceHookParam = HookParam(creatorInstance = this@YukiMemberHookCreator)

            /** 定义替换 Hook 回调方法体 */
            val replaceMent = object : YukiMemberReplacement(priority) {
                override fun replaceHookedMember(param: Param) =
                    replaceHookParam.assign(param).let { assign ->
                        runCatching {
                            replaceHookCallback?.invoke(assign).also {
                                checkingReturnType((param.member as? Method?)?.returnType, it?.javaClass)
                                if (replaceHookCallback != null) onHookLogMsg(msg = "Replace Hook Member [${this@hook}] done [$tag]")
                                HookParam.invoke()
                            }
                        }.getOrElse {
                            onConductFailureCallback?.invoke(assign, it)
                            onAllFailureCallback?.invoke(it)
                            if (onConductFailureCallback == null && onAllFailureCallback == null) onHookFailureMsg(it)
                            /** 若发生异常则会自动调用未经 Hook 的原始 [Member] 保证 Hook APP 正常运行 */
                            assign.callOriginal()
                        }
                    }
            }

            /** 定义前 Hook 的 [HookParam] */
            val beforeHookParam = HookParam(creatorInstance = this@YukiMemberHookCreator)

            /** 定义后 Hook 的 [HookParam] */
            val afterHookParam = HookParam(creatorInstance = this@YukiMemberHookCreator)

            /** 定义前后 Hook 回调方法体 */
            val beforeAfterHook = object : YukiMemberHook(priority) {
                override fun beforeHookedMember(param: Param) {
                    beforeHookParam.assign(param).also { assign ->
                        runCatching {
                            beforeHookCallback?.invoke(assign)
                            checkingReturnType((param.member as? Method?)?.returnType, param.result?.javaClass)
                            if (beforeHookCallback != null) onHookLogMsg(msg = "Before Hook Member [${this@hook}] done [$tag]")
                            HookParam.invoke()
                        }.onFailure {
                            onConductFailureCallback?.invoke(assign, it)
                            onAllFailureCallback?.invoke(it)
                            if (onConductFailureCallback == null && onAllFailureCallback == null) onHookFailureMsg(it)
                            if (isOnFailureThrowToApp) param.throwable = it
                        }
                    }
                }

                override fun afterHookedMember(param: Param) {
                    afterHookParam.assign(param).also { assign ->
                        runCatching {
                            afterHookCallback?.invoke(assign)
                            if (afterHookCallback != null) onHookLogMsg(msg = "After Hook Member [${this@hook}] done [$tag]")
                            HookParam.invoke()
                        }.onFailure {
                            onConductFailureCallback?.invoke(assign, it)
                            onAllFailureCallback?.invoke(it)
                            if (onConductFailureCallback == null && onAllFailureCallback == null) onHookFailureMsg(it)
                            if (isOnFailureThrowToApp) param.throwable = it
                        }
                    }
                }
            }
            return YukiHookHelper.hookMember(member = this, if (isReplaceHookMode) replaceMent else beforeAfterHook)
        }

        /**
         * 检查被 Hook [Member] 的返回值
         * @param origin 原始返回值
         * @param target 目标返回值
         * @throws IllegalStateException 如果返回值不正确
         */
        private fun checkingReturnType(origin: Class<*>?, target: Class<*>?) {
            /**
             * 获取当前 [Class] 的 Java 基本类型
             * @return [String]
             */
            fun Class<*>.objectName() =
                name.replace(Unit.toString(), newValue = "void")
                    .replace(oldValue = "java.lang.Void", newValue = "void")
                    .replace(oldValue = "java.lang.Boolean", newValue = "boolean")
                    .replace(oldValue = "java.lang.Integer", newValue = "int")
                    .replace(oldValue = "java.lang.Float", newValue = "float")
                    .replace(oldValue = "java.lang.Double", newValue = "double")
                    .replace(oldValue = "java.lang.Long", newValue = "long")
                    .replace(oldValue = "java.lang.Short", newValue = "short")
                    .replace(oldValue = "java.lang.Character", newValue = "char")
                    .replace(oldValue = "java.lang.Byte", newValue = "byte")
            if (origin == null || target == null) return
            val originName = origin.objectName()
            val targetName = target.objectName()
            if (originName != targetName) error("Hooked method return type match failed, required [$originName] but got [$targetName]")
        }

        /**
         * Hook 过程中开启了 [YukiHookAPI.Configs.isDebug] 输出调试信息
         * @param msg 调试日志内容
         */
        private fun onHookLogMsg(msg: String) {
            if (YukiHookAPI.Configs.isDebug) yLoggerI(msg = msg)
        }

        /**
         * Hook 失败但未设置 [onAllFailureCallback] 将默认输出失败信息
         * @param throwable 异常信息
         * @param member 异常 [Member] - 可空
         */
        private fun onHookFailureMsg(throwable: Throwable, member: Member? = null) = yLoggerE(
            msg = "Try to hook [${hookClass.instance ?: hookClass.name}]${member?.let { "[$it]" } ?: ""} got an Exception [$tag]",
            e = throwable
        )

        /**
         * 判断是否没有设置 Hook 过程中的任何异常拦截
         * @return [Boolean] 没有设置任何异常拦截
         */
        private val isNotIgnoredHookingFailure get() = onHookingFailureCallback == null && onAllFailureCallback == null

        /**
         * 判断是否没有设置 Hook 过程中 [members] 找不到的任何异常拦截
         * @return [Boolean] 没有设置任何异常拦截
         */
        internal val isNotIgnoredNoSuchMemberFailure get() = onNoSuchMemberFailureCallback == null && isNotIgnoredHookingFailure

        override fun toString() = "[tag] $tag [priority] $priority [class] $hookClass [members] $members"

        /**
         * Hook 方法体回调实现类
         */
        inner class HookCallback internal constructor() {

            /** 当回调方法体内发生异常时将异常抛出给当前 Hook APP */
            fun onFailureThrowToApp() {
                isOnFailureThrowToApp = true
            }
        }

        /**
         * 监听 Hook 结果实现类
         *
         * 可在这里处理失败事件监听
         */
        inner class Result internal constructor() {

            /**
             * 创建监听事件方法体
             * @param initiate 方法体
             * @return [Result] 可继续向下监听
             */
            inline fun result(initiate: Result.() -> Unit) = apply(initiate)

            /**
             * 添加执行 Hook 需要满足的条件
             *
             * 不满足条件将直接停止 Hook
             * @param condition 条件方法体
             * @return [Result] 可继续向下监听
             */
            inline fun by(condition: () -> Boolean): Result {
                isDisableMemberRunHook = (runCatching { condition() }.getOrNull() ?: false).not()
                if (isDisableMemberRunHook) ignoredAllFailure()
                return this
            }

            /**
             * 监听 [members] Hook 成功的回调方法
             *
             * 在首次 Hook 成功后回调
             *
             * 在重复 Hook 时会回调 [onAlreadyHooked]
             * @param result 回调被 Hook 的 [Member]
             * @return [Result] 可继续向下监听
             */
            fun onHooked(result: (Member) -> Unit): Result {
                onHookedCallback = result
                return this
            }

            /**
             * 监听 [members] 重复 Hook 的回调方法
             *
             * - ❗同一个 [hookClass] 中的同一个 [members] 不会被 API 重复 Hook - 若由于各种原因重复 Hook 会回调此方法
             * @param result 回调被重复 Hook 的 [Member]
             * @return [Result] 可继续向下监听
             */
            fun onAlreadyHooked(result: (Member) -> Unit): Result {
                onAlreadyHookedCallback = result
                return this
            }

            /**
             * 监听 [members] 不存在发生错误的回调方法
             * @param result 回调错误
             * @return [Result] 可继续向下监听
             */
            fun onNoSuchMemberFailure(result: (Throwable) -> Unit): Result {
                onNoSuchMemberFailureCallback = result
                return this
            }

            /**
             * 忽略 [members] 不存在发生的错误
             * @return [Result] 可继续向下监听
             */
            fun ignoredNoSuchMemberFailure() = onNoSuchMemberFailure {}

            /**
             * 监听 Hook 进行过程中发生错误的回调方法
             * @param result 回调错误 - ([HookParam] 当前 Hook 实例,[Throwable] 异常)
             * @return [Result] 可继续向下监听
             */
            fun onConductFailure(result: (HookParam, Throwable) -> Unit): Result {
                onConductFailureCallback = result
                return this
            }

            /**
             * 忽略 Hook 进行过程中发生的错误
             * @return [Result] 可继续向下监听
             */
            fun ignoredConductFailure() = onConductFailure { _, _ -> }

            /**
             * 监听 Hook 开始时发生错误的回调方法
             * @param result 回调错误
             * @return [Result] 可继续向下监听
             */
            fun onHookingFailure(result: (Throwable) -> Unit): Result {
                onHookingFailureCallback = result
                return this
            }

            /**
             * 忽略 Hook 开始时发生的错误
             * @return [Result] 可继续向下监听
             */
            fun ignoredHookingFailure() = onHookingFailure {}

            /**
             * 监听全部 Hook 过程发生错误的回调方法
             * @param result 回调错误
             * @return [Result] 可继续向下监听
             */
            fun onAllFailure(result: (Throwable) -> Unit): Result {
                onAllFailureCallback = result
                return this
            }

            /**
             * 忽略全部 Hook 过程发生的错误
             * @return [Result] 可继续向下监听
             */
            fun ignoredAllFailure() = onAllFailure {}

            /**
             * 移除当前注入的 Hook [Method]、[Constructor] (解除 Hook)
             *
             * - ❗你只能在 Hook 成功后才能解除 Hook - 可监听 [onHooked] 事件
             * @param result 回调是否成功
             */
            fun remove(result: (Boolean) -> Unit = {}) {
                memberUnhooks.takeIf { it.isNotEmpty() }?.apply {
                    forEach {
                        it.remove()
                        onHookLogMsg(msg = "Remove Hooked Member [${it.member}] done [$tag]")
                    }
                    runCatching { preHookMembers.remove(this@MemberHookCreator.toString()) }
                    clear()
                    result(true)
                } ?: result(false)
            }
        }
    }

    /**
     * 监听全部 Hook 结果实现类
     *
     * 可在这里处理失败事件监听
     */
    inner class Result internal constructor() {

        /** Hook 开始时的监听事件回调 */
        internal var onPrepareHook: (() -> Unit)? = null

        /**
         * 创建监听事件方法体
         * @param initiate 方法体
         * @return [Result] 可继续向下监听
         */
        inline fun result(initiate: Result.() -> Unit) = apply(initiate)

        /**
         * 添加执行 Hook 需要满足的条件
         *
         * 不满足条件将直接停止 Hook
         * @param condition 条件方法体
         * @return [Result] 可继续向下监听
         */
        inline fun by(condition: () -> Boolean): Result {
            isDisableCreatorRunHook = (runCatching { condition() }.getOrNull() ?: false).not()
            return this
        }

        /**
         * 监听 [hookClass] 存在时准备开始 Hook 的操作
         * @param callback 准备开始 Hook 后回调
         * @return [Result] 可继续向下监听
         */
        fun onPrepareHook(callback: () -> Unit): Result {
            onPrepareHook = callback
            return this
        }

        /**
         * 监听 [hookClass] 找不到时发生错误的回调方法
         * @param result 回调错误
         * @return [Result] 可继续向下监听
         */
        fun onHookClassNotFoundFailure(result: (Throwable) -> Unit): Result {
            onHookClassNotFoundFailureCallback = result
            return this
        }

        /**
         * 忽略 [hookClass] 找不到时出现的错误
         * @return [Result] 可继续向下监听
         */
        fun ignoredHookClassNotFoundFailure(): Result {
            by { hookClass.instance != null }
            return this
        }
    }
}