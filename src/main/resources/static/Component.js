sap.ui.define([
	"sap/ui/core/UIComponent",
	"sap/ui/Device",
	"ro/ubb/cs/re/jcm/model/models"
], function(UIComponent, Device, models) {
	"use strict";

	return UIComponent.extend("ro.ubb.cs.re.jcm.Component", {

		metadata: {
			manifest: "json"
		},

		/**
		 * The component is initialized by UI5 automatically during the startup of the app and calls the init method once.
		 * @public
		 * @override
		 */
		init: function() {
			// call the base component's init function
			UIComponent.prototype.init.apply(this, arguments);

			// set the device model
			this.setModel(models.createDeviceModel(), "device");
			
			// create the views based on the url/hash
			this.getRouter().initialize();
		},
		
		/**
		 * This method can be called to determine whether the sapUiSizeCompact or sapUiSizeCozy
		 * design mode class should be set, which influences the size appearance of some controls.
		 * @public
		 * @return {string} css class, either 'sapUiSizeCompact' or 'sapUiSizeCozy' - or an empty string if no css class should be set
		 */
		getContentDensityClass: function() {
			if (jQuery(document.body).hasClass("sapUiSizeCozy") || jQuery(document.body).hasClass("sapUiSizeCompact")) {
				// check whether FLP has already set the content density class; do nothing in this case
				return "";
			} else if (!Device.support.touch) { 
				// apply "compact" mode if touch is not supported
				return "sapUiSizeCompact";
			} else {
				// "cozy" in case of touch support; default for most sap.m controls, but needed for desktop-first controls like sap.ui.table.Table
				return "sapUiSizeCozy";
			}
		}
	});
});