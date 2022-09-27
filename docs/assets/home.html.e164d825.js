import{_ as e,o,c as i,a as d}from"./app.fb8271cf.js";const c={},t=d('<h1 id="\u6587\u6863\u4ECB\u7ECD" tabindex="-1"><a class="header-anchor" href="#\u6587\u6863\u4ECB\u7ECD" aria-hidden="true">#</a> \u6587\u6863\u4ECB\u7ECD</h1><blockquote><p>\u8FD9\u91CC\u7684\u6587\u6863\u5C06\u540C\u6B65\u6700\u65B0 API \u7248\u672C\u7684\u76F8\u5173\u7528\u6CD5\uFF0C\u8BF7\u4FDD\u6301 <code>YukiHookAPI</code> \u4E3A\u6700\u65B0\u7248\u672C\u4EE5\u4F7F\u7528\u6700\u65B0\u7248\u672C\u7684\u529F\u80FD\u3002</p></blockquote><h2 id="\u529F\u80FD\u63CF\u8FF0\u8BF4\u660E" tabindex="-1"><a class="header-anchor" href="#\u529F\u80FD\u63CF\u8FF0\u8BF4\u660E" aria-hidden="true">#</a> \u529F\u80FD\u63CF\u8FF0\u8BF4\u660E</h2><blockquote><p>\u529F\u80FD\u63CF\u8FF0\u4E3B\u8981\u4ECB\u7ECD\u5F53\u524D API \u7684\u76F8\u5173\u7528\u6CD5\u548C\u7528\u9014\u3002</p></blockquote><h2 id="\u529F\u80FD\u793A\u4F8B\u8BF4\u660E" tabindex="-1"><a class="header-anchor" href="#\u529F\u80FD\u793A\u4F8B\u8BF4\u660E" aria-hidden="true">#</a> \u529F\u80FD\u793A\u4F8B\u8BF4\u660E</h2><blockquote><p>\u529F\u80FD\u793A\u4F8B\u4E3B\u8981\u5C55\u793A\u4E86\u5F53\u524D API \u7684\u57FA\u672C\u7528\u6CD5\u793A\u4F8B\uFF0C\u53EF\u4F9B\u53C2\u8003\u3002</p></blockquote><h2 id="\u53D8\u66F4\u8BB0\u5F55\u8BF4\u660E" tabindex="-1"><a class="header-anchor" href="#\u53D8\u66F4\u8BB0\u5F55\u8BF4\u660E" aria-hidden="true">#</a> \u53D8\u66F4\u8BB0\u5F55\u8BF4\u660E</h2><p>\u9996\u4E2A\u7248\u672C\u7684\u529F\u80FD\u5C06\u6807\u8BB0\u4E3A <code>v&lt;version&gt;</code> <code>\u6DFB\u52A0</code>\uFF1B</p><p>\u540E\u671F\u65B0\u589E\u52A0\u7684\u529F\u80FD\u5C06\u6807\u8BB0\u4E3A <code>v&lt;version&gt;</code> <code>\u65B0\u589E</code>\uFF1B</p><p>\u540E\u671F\u4FEE\u6539\u7684\u529F\u80FD\u5C06\u88AB\u8FFD\u52A0\u4E3A <code>v&lt;version&gt;</code> <code>\u4FEE\u6539</code>\uFF1B</p><p>\u540E\u671F\u88AB\u4F5C\u5E9F\u7684\u529F\u80FD\u5C06\u6807\u8BB0\u4E3A <code>v&lt;version&gt;</code> <code>\u4F5C\u5E9F</code> \u5E76\u4F1A\u6807\u6CE8\u5220\u9664\u7EBF\uFF1B</p><p>\u540E\u671F\u88AB\u5220\u9664\u7684\u529F\u80FD\u5C06\u6807\u8BB0\u4E3A <code>v&lt;version&gt;</code> <code>\u79FB\u9664</code> \u5E76\u4F1A\u6807\u6CE8\u5220\u9664\u7EBF\u3002</p><h2 id="\u76F8\u5173\u7B26\u53F7\u8BF4\u660E" tabindex="-1"><a class="header-anchor" href="#\u76F8\u5173\u7B26\u53F7\u8BF4\u660E" aria-hidden="true">#</a> \u76F8\u5173\u7B26\u53F7\u8BF4\u660E</h2><ul><li><p><em>kt</em> \xA0Kotlin Static File</p></li><li><p><em>annotation</em> \xA0\u6CE8\u89E3</p></li><li><p><em>interface</em> \xA0\u63A5\u53E3</p></li><li><p><em>object</em> \xA0\u7C7B (\u5355\u4F8B)</p></li><li><p><em>class</em> \xA0\u7C7B</p></li><li><p><em>field</em> \xA0\u53D8\u91CF\u6216 <code>get</code>\u3001<code>set</code> \u65B9\u6CD5\u6216\u53EA\u8BFB\u7684 <code>get</code> \u65B9\u6CD5</p></li><li><p><em>method</em> \xA0\u65B9\u6CD5</p></li><li><p><em>enum</em> \xA0Enum \u5E38\u91CF</p></li><li><p><em>ext-field</em> \xA0\u6269\u5C55\u7684\u53D8\u91CF (\u5168\u5C40)</p></li><li><p><em>ext-method</em> \xA0\u6269\u5C55\u7684\u65B9\u6CD5 (\u5168\u5C40)</p></li><li><p><em>i-ext-field</em> \xA0\u6269\u5C55\u7684\u53D8\u91CF (\u8C03\u7528\u57DF\u9650\u5236)</p></li><li><p><em>i-ext-method</em> \xA0\u6269\u5C55\u7684\u65B9\u6CD5 (\u8C03\u7528\u57DF\u9650\u5236)</p></li></ul>',14),a=[t];function l(p,r){return o(),i("div",null,a)}const n=e(c,[["render",l],["__file","home.html.vue"]]);export{n as default};
