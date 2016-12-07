require.config({
    baseUrl: "js/",
    paths: {
        // jquery: "//code.jquery.com/jquery-2.2.2.min",
        // alight: "//angularlight.org/bin/alight_0.12.last.min",
        // lodash: "//cdn.jsdelivr.net/lodash/4.8.2/lodash.min",
        jquery: "lib/jquery-2.2.2",
        jqueryUI: "//code.jquery.com/ui/1.11.4/jquery-ui",
        contextMenu: "lib/jquery.contextMenu",
        alight: "lib/alight_0.12.last.debug",
        lodash: "lib/lodash_4.6.1",
        split: "lib/split",
        routie: "lib/routie",
        PDFObject: "lib/pdfobject",
        bootstrap: "lib/bootstrap"
    },
    shim: {
        bootstrap: {
            deps: ["jquery"]
        }
    },
    waitSeconds: 30
});

// requirejs(["app/main"]);
require([
    "alight",
    "app/service/routes",

    "app/service/context-menu",
    "app/controller/providers.controller",
    "app/directive/files.directive",
    "app/filter/capitalize.filter",
    "bootstrap"
], function (alight, routes) {
    if (!window.location.hash) {
        routes.changePath();
    }

    alight.bootstrap();
});