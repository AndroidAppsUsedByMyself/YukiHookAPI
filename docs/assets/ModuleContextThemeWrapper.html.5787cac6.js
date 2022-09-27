import{_ as s,o,c as e,a as n}from"./app.fb8271cf.js";const a={},t=n(`<div class="custom-container warning"><p class="custom-container-title">Notice</p><p>The English translation of this page has not been completed, you are welcome to contribute translations to us.</p><p>You can use the <strong>Chrome Translation Plugin</strong> to translate entire pages for reference.</p></div><h1 id="modulecontextthemewrapper-class" tabindex="-1"><a class="header-anchor" href="#modulecontextthemewrapper-class" aria-hidden="true">#</a> ModuleContextThemeWrapper <span class="symbol">- class</span></h1><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">class</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">ModuleContextThemeWrapper</span><span style="color:#ADBAC7;"> </span><span style="color:#F47067;">private</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">constructor</span><span style="color:#ADBAC7;">(baseContext</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">Context</span><span style="color:#ADBAC7;">, theme</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">Int</span><span style="color:#ADBAC7;">, configuration</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">Configuration?</span><span style="color:#ADBAC7;">) </span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">ContextThemeWrapper</span></span>
<span class="line"></span></code></pre></div><p><strong>Change Records</strong></p><p><code>v1.1.0</code> <code>added</code></p><p><strong>Function Illustrate</strong></p><blockquote><p>\u4EE3\u7406 <code>ContextThemeWrapper</code>\u3002</p></blockquote><p>\u901A\u8FC7\u5305\u88C5\uFF0C\u4F60\u53EF\u4EE5\u8F7B\u677E\u5728 (Xposed) \u5BBF\u4E3B\u73AF\u5883\u4F7F\u7528\u6765\u81EA\u6A21\u5757\u7684\u4E3B\u9898\u8D44\u6E90\u3002</p><h2 id="applyconfiguration-method" tabindex="-1"><a class="header-anchor" href="#applyconfiguration-method" aria-hidden="true">#</a> applyConfiguration <span class="symbol">- method</span></h2><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">applyConfiguration</span><span style="color:#ADBAC7;">(initiate</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">Configuration</span><span style="color:#ADBAC7;">.() </span><span style="color:#F47067;">-&gt;</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">Unit</span><span style="color:#ADBAC7;">)</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">ModuleContextThemeWrapper</span></span>
<span class="line"></span></code></pre></div><p><strong>Change Records</strong></p><p><code>v1.1.0</code> <code>added</code></p><p><strong>Function Illustrate</strong></p><blockquote><p>\u8BBE\u7F6E\u5F53\u524D <code>ModuleContextThemeWrapper</code> \u7684 <code>Configuration</code>\u3002</p></blockquote><p>\u8BBE\u7F6E\u540E\u4F1A\u81EA\u52A8\u8C03\u7528 <code>Resources.updateConfiguration</code>\u3002</p>`,15),p=[t];function l(r,c){return o(),e("div",null,p)}const d=s(a,[["render",l],["__file","ModuleContextThemeWrapper.html.vue"]]);export{d as default};
