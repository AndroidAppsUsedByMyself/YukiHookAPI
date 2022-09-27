import{_ as s,o as e,c as o,a as n}from"./app.fb8271cf.js";const a={},p=n(`<h1 id="basefinder-class" tabindex="-1"><a class="header-anchor" href="#basefinder-class" aria-hidden="true">#</a> BaseFinder <span class="symbol">- class</span></h1><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">abstract</span><span style="color:#ADBAC7;"> </span><span style="color:#F47067;">class</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">BaseFinder</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><code>v1.1.0</code> <code>\u4FEE\u6539</code></p><p>\u5206\u79BB\u539F\u59CB\u547D\u540D <code>BaseFinder</code> \u4E2D\u7684\u90E8\u5206\u65B9\u6CD5\u4E0E\u53C2\u6570\u5230 <code>MemberBaseFinder</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u8FD9\u662F <code>Class</code> \u4E0E <code>Member</code> \u67E5\u627E\u7C7B\u529F\u80FD\u7684\u57FA\u672C\u7C7B\u5B9E\u73B0\u3002</p></blockquote><h2 id="basefinder-indextypecondition-class" tabindex="-1"><a class="header-anchor" href="#basefinder-indextypecondition-class" aria-hidden="true">#</a> BaseFinder.IndexTypeCondition <span class="symbol">- class</span></h2><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#ADBAC7;">inner </span><span style="color:#F47067;">class</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">IndexTypeCondition</span><span style="color:#ADBAC7;"> </span><span style="color:#F47067;">internal</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">constructor</span><span style="color:#ADBAC7;">(</span><span style="color:#F47067;">private</span><span style="color:#ADBAC7;"> </span><span style="color:#F47067;">val</span><span style="color:#ADBAC7;"> type</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">IndexConfigType</span><span style="color:#ADBAC7;">)</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u5B57\u8282\u7801\u4E0B\u6807\u7B5B\u9009\u5B9E\u73B0\u7C7B\u3002</p></blockquote><h3 id="index-method" tabindex="-1"><a class="header-anchor" href="#index-method" aria-hidden="true">#</a> index <span class="symbol">- method</span></h3><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">index</span><span style="color:#ADBAC7;">(num</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">Int</span><span style="color:#ADBAC7;">)</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u8BBE\u7F6E\u4E0B\u6807\u3002</p></blockquote><p>\u82E5 <code>index</code> \u5C0F\u4E8E\u96F6\u5219\u4E3A\u5012\u5E8F\uFF0C\u6B64\u65F6\u53EF\u4EE5\u4F7F\u7528 <code>IndexTypeConditionSort.reverse</code> \u65B9\u6CD5\u5B9E\u73B0\u3002</p><p>\u53EF\u4F7F\u7528 <code>IndexTypeConditionSort.first</code> \u548C <code>IndexTypeConditionSort.last</code> \u8BBE\u7F6E\u9996\u4F4D\u548C\u672B\u4F4D\u7B5B\u9009\u6761\u4EF6\u3002</p><h3 id="index-method-1" tabindex="-1"><a class="header-anchor" href="#index-method-1" aria-hidden="true">#</a> index <span class="symbol">- method</span></h3><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">index</span><span style="color:#ADBAC7;">()</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">IndexTypeConditionSort</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u5F97\u5230\u4E0B\u6807\u3002</p></blockquote><h3 id="indextypeconditionsort-class" tabindex="-1"><a class="header-anchor" href="#indextypeconditionsort-class" aria-hidden="true">#</a> IndexTypeConditionSort <span class="symbol">- class</span></h3><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#ADBAC7;">inner </span><span style="color:#F47067;">class</span><span style="color:#ADBAC7;"> </span><span style="color:#F69D50;">IndexTypeConditionSort</span><span style="color:#ADBAC7;"> </span><span style="color:#F47067;">internal</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">constructor</span><span style="color:#ADBAC7;">()</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u5B57\u8282\u7801\u4E0B\u6807\u6392\u5E8F\u5B9E\u73B0\u7C7B\u3002</p></blockquote><h4 id="first-method" tabindex="-1"><a class="header-anchor" href="#first-method" aria-hidden="true">#</a> first <span class="symbol">- method</span></h4><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">first</span><span style="color:#ADBAC7;">()</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u8BBE\u7F6E\u6EE1\u8DB3\u6761\u4EF6\u7684\u7B2C\u4E00\u4E2A\u3002</p></blockquote><h4 id="last-method" tabindex="-1"><a class="header-anchor" href="#last-method" aria-hidden="true">#</a> last <span class="symbol">- method</span></h4><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">last</span><span style="color:#ADBAC7;">()</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u8BBE\u7F6E\u6EE1\u8DB3\u6761\u4EF6\u7684\u6700\u540E\u4E00\u4E2A\u3002</p></blockquote><h4 id="reverse-method" tabindex="-1"><a class="header-anchor" href="#reverse-method" aria-hidden="true">#</a> reverse <span class="symbol">- method</span></h4><div class="language-kotlin ext-kt"><pre class="shiki" style="background-color:#22272e;"><code><span class="line"><span style="color:#F47067;">fun</span><span style="color:#ADBAC7;"> </span><span style="color:#DCBDFB;">reverse</span><span style="color:#ADBAC7;">(num</span><span style="color:#F47067;">:</span><span style="color:#ADBAC7;"> </span><span style="color:#6CB6FF;">Int</span><span style="color:#ADBAC7;">)</span></span>
<span class="line"></span></code></pre></div><p><strong>\u53D8\u66F4\u8BB0\u5F55</strong></p><p><code>v1.0.70</code> <code>\u65B0\u589E</code></p><p><strong>\u529F\u80FD\u63CF\u8FF0</strong></p><blockquote><p>\u8BBE\u7F6E\u5012\u5E8F\u4E0B\u6807\u3002</p></blockquote>`,52),l=[p];function c(t,r){return e(),o("div",null,l)}const i=s(a,[["render",c],["__file","BaseFinder.html.vue"]]);export{i as default};
