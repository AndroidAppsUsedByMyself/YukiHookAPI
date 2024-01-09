import{_ as e,o,c as a,a as r}from"./app-Dngl3WKO.js";const n={},s=r(`<h1 id="r8-proguard-obfuscate" tabindex="-1"><a class="header-anchor" href="#r8-proguard-obfuscate" aria-hidden="true">#</a> R8 &amp; Proguard Obfuscate</h1><blockquote><p>In most scenarios, the Xposed Module can be compressed by native obfuscation.</p><p>Here is the configuration method of obfuscation.</p></blockquote><h2 id="r8" tabindex="-1"><a class="header-anchor" href="#r8" aria-hidden="true">#</a> R8</h2><blockquote><p>If you are using <code>R8</code> then you don&#39;t need any special configuration for <code>YukiHookAPI</code>.</p></blockquote><h2 id="proguard" tabindex="-1"><a class="header-anchor" href="#proguard" aria-hidden="true">#</a> Proguard</h2><blockquote><p><s>If you are still using <code>Proguard</code>, you need to do some rule configuration.</s></p></blockquote><div class="custom-container danger"><p class="custom-container-title">Pay Attention</p><p>Proguard rules have been deprecated, please don&#39;t use them anymore.</p><p>Since Android Gradle Plugin 4.2, the obfuscator with the latest version of the Android Jetpack default is <strong>R8</strong>, and you no longer need to consider obfuscation.</p></div><p>To enable <code>R8</code> in any version, please add the following rules to the <code>gradle.properties</code> file, no configuration is required for Android Gradle Plugin 7.0 and above.</p><div class="language-groovy line-numbers-mode" data-ext="groovy"><pre class="shiki github-dark-dimmed" style="background-color:#22272e;" tabindex="0"><code><span class="line"><span style="color:#ADBAC7;">android</span><span style="color:#F47067;">.</span><span style="color:#ADBAC7;">enableR8</span><span style="color:#F47067;">=</span><span style="color:#6CB6FF;">true</span></span>
<span class="line"></span></code></pre><div class="line-numbers" aria-hidden="true"><div class="line-number"></div></div></div>`,9),d=[s];function t(i,c){return o(),a("div",null,d)}const u=e(n,[["render",t],["__file","r8-proguard.html.vue"]]);export{u as default};
