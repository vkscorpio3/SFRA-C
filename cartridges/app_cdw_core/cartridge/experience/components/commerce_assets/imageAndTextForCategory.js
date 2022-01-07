'use strict';

var Template = require('dw/util/Template');
var HashMap = require('dw/util/HashMap');
var ImageTransformation = require('*/cartridge/experience/utilities/ImageTransformation.js');


/**
 * Render logic for storefront.imageAndText component.
 * @param {dw.experience.ComponentScriptContext} context The Component script context object.
 * @param {dw.util.Map} [modelIn] Additional model values created by another cartridge. This will not be passed in by Commcerce Cloud Plattform.
 *
 * @returns {string} The markup to be displayed
 */
module.exports.render = function (context, modelIn) {
    var model = modelIn || new HashMap();
    var content = context.content;

    model.textOverlay1 = content.textOverlay1 ? content.textOverlay1 : null;
    model.textOverlay2 = content.textOverlay2 ? content.textOverlay2 : null;
    model.ITCText = content.ITCText ? content.ITCText : null;
    model.image = ImageTransformation.getScaledImage(content.image);

    var category = content.ITCLink;
    if(!empty(category)) {  

        if(category.custom && 'alternativeUrl' in category.custom && category.custom.alternativeUrl) {
            if(category.custom.alternativeUrl.toString().indexOf('?') == -1) {
                model.link = (category.custom.alternativeUrl.toString()).replace(/&amp;/g, '&')+"?oci="+category.ID;
            }else {
                model.link = (category.custom.alternativeUrl.toString()).replace(/&amp;/g, '&')+"&oci="+category.ID;
            }
            
        }else {
            model.link =  URLUtils.url('Search-Show', 'cgid', category.getID()).toString();
        }

    }else {
        model.link = '#';
    }
    
    model.alt = content.alt ? content.alt : null;

    return new Template('experience/components/commerce_assets/imageAndText').render(model).text;
};
