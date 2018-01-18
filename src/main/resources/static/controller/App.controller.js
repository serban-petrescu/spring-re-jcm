sap.ui.define([
	"./BaseController"
], function(BaseController) {
	"use strict";

	return BaseController.extend("ro.ubb.cs.re.jcm.controller.App", {

		onInit: function() {
			this.getView().addStyleClass(this.getOwnerComponent().getContentDensityClass());
		}
	});

});