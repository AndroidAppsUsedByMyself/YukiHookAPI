import{_ as o,o as e,c as s,a as n}from"./app.fb8271cf.js";const a={},t=n(`<div class="custom-container warning"><p class="custom-container-title">Notice</p><p>The English translation of this page has not been completed, you are welcome to contribute translations to us.</p><p>You can use the <strong>Chrome Translation Plugin</strong> to translate entire pages for reference.</p></div><h1 id="iyukihookxposedinit-interface" tabindex="-1"><a class="header-anchor" href="#iyukihookxposedinit-interface" aria-hidden="true">#</a> IYukiHookXposedInit <span class="symbol">- interface</span></h1><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">interface</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">IYukiHookXposedInit</span></span>
<span class="line"></span></code></pre></div><p><strong>Change Records</strong></p><p><code>v1.0</code> <code>first</code></p><p><code>v1.0.80</code> <code>modified</code> <code>deprecated</code></p><p>\u4F5C\u5E9F\u4E86 <s><code>YukiHookXposedInitProxy</code></s> \u540D\u79F0\u4F46\u4FDD\u7559\u63A5\u53E3</p><p>\u8F6C\u79FB\u5230 <code>IYukiHookXposedInit</code> \u65B0\u540D\u79F0</p><p><strong>Function Illustrate</strong></p><blockquote><p>YukiHookAPI \u7684 Xposed \u88C5\u8F7D API \u8C03\u7528\u63A5\u53E3\u3002</p></blockquote><h2 id="oninit-method" tabindex="-1"><a class="header-anchor" href="#oninit-method" aria-hidden="true">#</a> onInit <span class="symbol">- method</span></h2><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">onInit</span><span style="color:#ADBAC7;">()</span></span>
<span class="line"></span></code></pre></div><p><strong>Change Records</strong></p><p><code>v1.0.5</code> <code>added</code></p><p><strong>Function Illustrate</strong></p><blockquote><p>\u914D\u7F6E <code>YukiHookAPI.Configs</code> \u7684\u521D\u59CB\u5316\u65B9\u6CD5\u3002</p></blockquote><div class="custom-container danger"><p class="custom-container-title">Pay Attention</p><p>\u5728\u8FD9\u91CC\u53EA\u80FD\u8FDB\u884C\u521D\u59CB\u5316\u914D\u7F6E\uFF0C\u4E0D\u80FD\u8FDB\u884C Hook \u64CD\u4F5C\u3002</p></div><p>\u6B64\u65B9\u6CD5\u53EF\u9009\uFF0C\u4F60\u4E5F\u53EF\u4EE5\u9009\u62E9\u4E0D\u5BF9 <a href="../../../YukiHookAPI#configs-object">YukiHookAPI.Configs</a> \u8FDB\u884C\u914D\u7F6E\u3002</p><h2 id="onhook-method" tabindex="-1"><a class="header-anchor" href="#onhook-method" aria-hidden="true">#</a> onHook <span class="symbol">- method</span></h2><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">onHook</span><span style="color:#ADBAC7;">()</span></span>
<span class="line"></span></code></pre></div><p><strong>Change Records</strong></p><p><code>v1.0</code> <code>first</code></p><p><strong>Function Illustrate</strong></p><blockquote><p>Xposed API \u7684\u6A21\u5757\u88C5\u8F7D\u8C03\u7528\u5165\u53E3\u65B9\u6CD5\u3002</p></blockquote><h2 id="onxposedevent-method" tabindex="-1"><a class="header-anchor" href="#onxposedevent-method" aria-hidden="true">#</a> onXposedEvent <span class="symbol">- method</span></h2><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">onXposedEvent</span><span style="color:#ADBAC7;">()</span></span>
<span class="line"></span></code></pre></div><p><strong>Change Records</strong></p><p><code>v1.0.80</code> <code>added</code></p><p><strong>Function Illustrate</strong></p><blockquote><p>\u76D1\u542C Xposed \u539F\u751F\u88C5\u8F7D\u4E8B\u4EF6\u3002</p></blockquote><p>\u82E5\u4F60\u7684 Hook \u4E8B\u4EF6\u4E2D\u5B58\u5728\u9700\u8981\u517C\u5BB9\u7684\u539F\u751F Xposed \u529F\u80FD\uFF0C\u53EF\u5728\u8FD9\u91CC\u5B9E\u73B0\u3002</p><p>\u8BF7\u5728\u8FD9\u91CC\u4F7F\u7528 <a href="../bridge/event/YukiXposedEvent">YukiXposedEvent</a> \u521B\u5EFA\u56DE\u8C03\u4E8B\u4EF6\u76D1\u542C\u3002</p><p>\u53EF\u76D1\u542C\u7684\u4E8B\u4EF6\u5982\u4E0B\uFF1A</p><p><code>YukiXposedEvent.onInitZygote</code></p><p><code>YukiXposedEvent.onHandleLoadPackage</code></p><p><code>YukiXposedEvent.onHandleInitPackageResources</code></p><div class="custom-container danger"><p class="custom-container-title">Pay Attention</p><p>\u6B64\u63A5\u53E3\u4EC5\u4F9B\u76D1\u542C\u548C\u5B9E\u73B0\u539F\u751F Xposed API \u7684\u529F\u80FD\uFF0C\u8BF7\u4E0D\u8981\u5728\u8FD9\u91CC\u64CD\u4F5C <strong>YukiHookAPI</strong>\u3002</p></div><h1 class="deprecated">YukiHookXposedInitProxy - interface</h1><p><strong>Change Records</strong></p><p><code>v1.0</code> <code>first</code></p><p><code>v1.0.80</code> <code>deprecated</code></p><p>\u8BF7\u8F6C\u79FB\u5230 <code>IYukiHookXposedInit</code></p>`,42),c=[t];function p(d,i){return e(),s("div",null,c)}const l=o(a,[["render",p],["__file","IYukiHookXposedInit.html.vue"]]);export{l as default};
