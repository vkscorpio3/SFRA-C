'use strict';

/**
 * @namespace Home
 */

var server = require('server');
server.extend(module.superModule);
var cache = require('*/cartridge/scripts/middleware/cache');
var consentTracking = require('*/cartridge/scripts/middleware/consentTracking');
var pageMetaData = require('*/cartridge/scripts/middleware/pageMetaData');
var Resource = require('dw/web/Resource');

/**
 * Any customization on this endpoint, also requires update for Default-Start endpoint
 */
/**
 * Home-Show : This endpoint is called when a shopper navigates to the home page
 * @name Base/Home-Show
 * @function
 * @memberof Home
 * @param {middleware} - consentTracking.consent
 * @param {middleware} - cache.applyDefaultCache
 * @param {category} - non-sensitive
 * @param {renders} - isml
 * @param {serverfunction} - get
 */
server.append('Show', consentTracking.consent, cache.applyDefaultCache, function (req, res, next) {
    var Site = require('dw/system/Site');
    var PageMgr = require('dw/experience/PageMgr');
    var pageMetaHelper = require('*/cartridge/scripts/helpers/pageMetaHelper');
    var URLUtils = require('dw/web/URLUtils');
    var HashMap = require('dw/util/HashMap');
    var Logger = require('dw/system/Logger');
    var logger = Logger.getLogger("Checkout","checkout");

    var homePageId = Site.current.getCustomPreferenceValue('pageDesignerHomePageId') || "homepage";

    pageMetaHelper.setPageMetaTags(req.pageMetaData, Site.current);
    var aspectAttributes = new HashMap();
    var pdHomePage = PageMgr.getPage(homePageId);

    var siteHostName = Site.current.getCustomPreferenceValue('siteHostName') || 'https://www.cdw.com';
    var schemaDataVar =    {
                                "@context": "https://schema.org",
                                "@type": "Corporation",
                                "name": "cdw Tools",
                                "url": siteHostName,
                                "logo": siteHostName + URLUtils.staticURL('/images/CDWStarterStoreLogo.svg').toString(),
                                "sameAs": [
                                    "https://facebook.com/cdwtools",
                                    "https://instagram.com/cdwtools",
                                    "https://twitter.com/cdwtools",
                                    "https://www.linkedin.com/company/cdw.com/",
                                    "https://www.youtube.com/cdwtoolstv",
                                    "https://www.pinterest.com/cdwtools/",
                                    "https://www.tiktok.com/@cdwtools"
                                ],
                                "contactPoint": [
                                    {
                                    "@type": "ContactPoint",
                                    "telephone": "1-877-345-2263",
                                    "contactType": "customer service",
                                    "email": "support@cdw.com",
                                    "contactOption": "TollFree",
                                    "areaServed": "US"
                                    }
                                ],
                                "potentialAction": {
                                    "@type": "SearchAction",
                                    "target": "https://www.cdw.com/search?q={search_term_string}",
                                    "query-input": "required name=search_term_string"
                                }
                            } ;

    res.setViewData({
        schemaData: schemaDataVar
    });

    if (pdHomePage) {
        res.setViewData({
            canonicalUrl: "/",
            gtmPageType: 'TOMS One Column Page'
        });
        aspectAttributes.canonicalUrl = "/";
        aspectAttributes.schemaData = schemaDataVar;
        res.page(pdHomePage.ID, {schemaData: schemaDataVar}, aspectAttributes);
        // res.page(pdHomePageId);
    } else {
        logger.warn('Page designer content page with ID {0} is unpublished', homePageId);
        res.render('/home/homePage');
    }
    // res.render('/home/homePage');
    next();
}, pageMetaData.computedPageMetaData);

server.append('ErrorNotFound', function (req, res, next) {

    var computedMetaData = {
        title: Resource.msg('404.page.title', 'common', null),
        description: Resource.msg('404.page.description', 'common', null),
        keywords: Resource.msg('404.page.keywords', 'common', null),
        pageMetaTags: []
    };

    var pageGroup = {name: Resource.msg('404.page.pageGroup.name', 'common', null),
                    ID: Resource.msg('404.page.pageGroup.name', 'common', null),
                    content: Resource.msg('404.page.pageGroup.value', 'common', null)};
    var robots = {name: Resource.msg('404.page.robots.name', 'common', null),
                    ID: Resource.msg('404.page.robots.name', 'common', null),
                    content: Resource.msg('404.page.robots.value', 'cacommonrt', null)};                    
    computedMetaData.pageMetaTags.push(pageGroup);
    computedMetaData.pageMetaTags.push(robots);

    res.setViewData({
        CurrentPageMetaData: computedMetaData
    });

    next();
});

module.exports = server.exports();
