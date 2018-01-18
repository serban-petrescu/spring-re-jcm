sap.ui.define([
	"./BaseController",
	"sap/ui/model/json/JSONModel",
	"sap/ui/model/Filter"
], function(BaseController, JSONModel, Filter) {
	"use strict";

    function arrayToObject(aArray) {
        var oResult = {};
        for (var i = 0; i < aArray.length; ++i) {
            oResult[aArray[i].id] = aArray[i];
        }
        return oResult;
    }

	return BaseController.extend("ro.ubb.cs.re.jcm.controller.CandidateDetails", {

		onInit: function() {
            var oRouter = this.getOwnerComponent().getRouter();
            this.setModel(new JSONModel({
                edit: false,
                universities: [],
                loading: false,
                create: false
            }));
            this.loadUniversities();
            oRouter.getRoute("candidateCreate").attachPatternMatched(this.onOpenInCreateMode, this);
            oRouter.getRoute("candidateDetails").attachPatternMatched(this.onOpenInDisplayMode, this);
		},

		loadUniversities: function() {
            var oModel = this.getModel();
            jQuery.ajax({
                url: "/rest/universities",
                dataType: "json",
                success: function(aUniversities) {
                    aUniversities.forEach(function(oU){oU.specializations.forEach(function(oS){oS.university = oU.id})});
                    var aSpecializations = [].concat.apply([], aUniversities.map(function(oU){return oU.specializations}));
                    oModel.setProperty("/universities", arrayToObject(aUniversities));
                    oModel.setProperty("/specializations", arrayToObject(aSpecializations));
                    if (oModel.getProperty("/data/id")) {
                        this.updateUniversityBasedOnSpecialization();
                    }
                }.bind(this),
                error: this.onError
            });
		},

		onOpenInCreateMode: function() {
            var oModel = this.getModel();
            oModel.setProperty("/edit", true);
            oModel.setProperty("/create", true);
            oModel.setProperty("/data", {
                firstName: "",
                lastName: "",
                university: null,
                specialization: null,
                studyYear: 1,
                email: "",
                phone: ""
            });
            this.byId("specialization").getBinding("items").filter([]);
		},

		onOpenInDisplayMode: function(oEvent) {
            var oModel = this.getModel(),
                sId = oEvent.getParameter("arguments").id;
            oModel.setProperty("/edit", false);
            oModel.setProperty("/create", false);
            jQuery.ajax({
                url: "/rest/candidates/" + sId,
                method: "GET",
                dataType: "json",
                success: function(oData) {
                    oModel.setProperty("/data", oData);
                    this.updateUniversityBasedOnSpecialization();
                }.bind(this),
                error: this.onError
            });
		},

		updateUniversityBasedOnSpecialization: function() {
            var oModel = this.getModel(),
                iSpecialization = oModel.getProperty("/data/specialization"),
                iUniversity = oModel.getProperty("/specializations/" + iSpecialization + "/university");
            this.byId("specialization").getBinding("items").filter([new Filter("university", "EQ", iUniversity)]);
            oModel.setProperty("/data/university", iUniversity);
		},

        onSave: function() {
            var oModel = this.getModel(),
                oRouter = this.getOwnerComponent().getRouter();
            if (oModel.getProperty("/create")) {
                jQuery.ajax({
                    url: "/rest/candidates",
                    method: "POST",
                    data: JSON.stringify(oModel.getProperty("/data")),
                    contentType: "application/json",
                    dataType: "json",
                    success: function(oData) {
                        oRouter.navTo("candidateDetails", {id: oData.id}, true);
                    },
                    error: this.onError
                });
            } else {
                jQuery.ajax({
                    url: "/rest/candidates/" + oModel.getProperty("/data/id"),
                    method: "PUT",
                    data: JSON.stringify(oModel.getProperty("/data")),
                    contentType: "application/json",
                    success: function() {
                        oModel.setProperty("/edit", false);
                    },
                    error: this.onError
                });
            }
        },

        onEdit: function() {
            this.getModel().setProperty("/edit", true);
        },

        onCancel: function() {
            if (this.getModel().getProperty("/create")) {
                this.getOwnerComponent().getRouter().navTo("candidateList");
            } else {
                this.getModel().setProperty("/edit", false)
            }
        },

        onDelete: function() {
            var oModel = this.getModel(),
                oRouter = this.getOwnerComponent().getRouter();
            jQuery.ajax({
                url: "/rest/candidates/" + oModel.getProperty("/data/id"),
                method: "DELETE",
                success: function() {
                    oRouter.navTo("candidateList");
                },
                error: this.onError
            });
        },

		onUniversityChange: function(oEvent) {
            var iId = oEvent.getSource().getSelectedItem().getBindingContext().getProperty("id");
            this.byId("specialization").getBinding("items").filter([new Filter("university", "EQ", iId)]);
            this.byId("specialization").setSelectedItem(null);
		}
	});

});