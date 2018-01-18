sap.ui.define([
	"./BaseController",
	"sap/ui/model/json/JSONModel",
	"jquery.sap.global",
	"sap/ui/model/Filter",
    "sap/ui/model/Sorter",
    "sap/ui/core/util/File"
], function(BaseController, JSONModel, jQuery, Filter, Sorter, File) {
	"use strict";

	return BaseController.extend("ro.ubb.cs.re.jcm.controller.CandidateList", {
        onInit: function() {
            var oRouter = this.getOwnerComponent().getRouter();
            oRouter.getRoute("candidateList").attachPatternMatched(this.onDisplay, this);
            oRouter.attachBypassed(this.onDisplay, this);
            this.setModel(new JSONModel({loading: true, candidates: [], sort: {direction: "asc", key: "none"}}));
            this.byId("popover").addStyleClass(this.getOwnerComponent().getContentDensityClass());
        },

        onDisplay: function() {
            var oModel = this.getModel();
            oModel.setProperty("/loading", true);
            jQuery.ajax({
                url: "/rest/candidates",
                dataType: "json",
                success: function(aCandidates) {
                    oModel.setProperty("/candidates", aCandidates);
                    oModel.setProperty("/loading", false);
                },
                error: this.onError
            });
        },

        toRomanNumeral: function(iNumber) {
            if (!+iNumber) {
                return NaN;
            }
            var aDigits = String(+iNumber).split(""),
                aKey = ["","C","CC","CCC","CD","D","DC","DCC","DCCC","CM",
                       "","X","XX","XXX","XL","L","LX","LXX","LXXX","XC",
                       "","I","II","III","IV","V","VI","VII","VIII","IX"],
                sRoman = "",
                i = 3;
            while (i--) {
                sRoman = (aKey[+aDigits.pop() + (i * 10)] || "") + sRoman;
            }
            return Array(+aDigits.join("") + 1).join("M") + sRoman;
        },

        onSortButtonPress: function(oEvent) {
            this.byId("popover").openBy(oEvent.getSource());
        },

        onCancelSort: function() {
            this.byId("popover").close();
        },

        onAcceptSort: function() {
            var oBinding = this.byId("table").getBinding("items"),
                oSort = this.getModel().getProperty("/sort");
            if (oSort.key !== "none") {
                oBinding.sort([new Sorter(oSort.key, oSort.direction === "desc")]);
            } else {
                oBinding.sort([]);
            }
            this.byId("popover").close();
        },

        onSearch: function(oEvent) {
            var sQuery = oEvent.getParameter("query"),
                oBinding = this.byId("table").getBinding("items");
            if (sQuery) {
                var sLower = sQuery.toLowerCase(),
                    fnRoman = this.toRomanNumeral,
                    fnTest = function(sValue) {
                        return (sValue || "").toLowerCase().indexOf(sLower) >= 0;
                    },
                    fnTestYear = function(iValue) {
                        return fnTest(fnRoman(iValue));
                    },
                    aFilters = [
                        new Filter({path: "fullName", test: fnTest}),
                        new Filter({path: "studyLocation", test: fnTest}),
                        new Filter({path: "studyYear", test: fnTestYear}),
                        new Filter({path: "email", test: fnTest})
                    ];
                oBinding.filter([new Filter(aFilters, false)]);
            } else {
                oBinding.filter([]);
            }
        },

        onExportSelection: function() {
            var aIds = (this.byId("table").getSelectedContexts() || [])
                .map(function(oContext) {return oContext.getProperty("id")});
            jQuery.ajax({
                url: "/rest/candidates/export",
                method: "POST",
                data: JSON.stringify(aIds),
                contentType: "application/json",
                dataType: "text",
                success: function(sData) {
                    File.save(sData, "candidates", "csv");
                },
                error: this.onError
            });
        },

        onAddButtonPress: function() {
            this.getOwnerComponent().getRouter().navTo("candidateCreate");
        },

        onItemPress: function(oEvent) {
            var id = oEvent.getSource().getBindingContext().getProperty("id");
            this.getOwnerComponent().getRouter().navTo("candidateDetails", {id: id});
        }
	});
});