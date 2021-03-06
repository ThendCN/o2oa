MWF.xDesktop.requireApp("process.Xform", "$Input", null, false);
MWF.xApplication.process.Xform.Calendar = MWF.APPCalendar =  new Class({
	Implements: [Events],
	Extends: MWF.APP$Input,
	iconStyle: "calendarIcon",
    options: {
        "moduleEvents": ["complete", "clear"]
    },
    setDescriptionEvent: function(){
        if (this.descriptionNode){
            this.descriptionNode.addEvents({
                "mousedown": function(){
                    this.descriptionNode.setStyle("display", "none");
                    this.clickSelect();
                }.bind(this)
            });
        }
    },
	clickSelect: function(){
        if (!this.calendar){
            MWF.require("MWF.widget.Calendar", function(){
                this.calendar = new MWF.widget.Calendar(this.node.getFirst(), {
                    "style": "xform",
                    "isTime": (this.json.selectType=="datetime"),
                    //"target": this.form.node,
                    "target": this.form.app.content,
                    "format": this.json.format,
                    "onComplate": function(){
                        this.validationMode();
                        this.validation();
                        this.fireEvent("complete");
                    }.bind(this),
                    "onClear": function(){
                        this.validationMode();
                        this.validation();
                        this.fireEvent("clear");
                        if (!this.node.getFirst().get("value")) if (this.descriptionNode)  this.descriptionNode.setStyle("display", "block");
                    }.bind(this),
                    "onShow": function(){
                        if (this.descriptionNode) this.descriptionNode.setStyle("display", "none");
                    }.bind(this),
                    "onHide": function(){
                        if (!this.node.getFirst().get("value")) if (this.descriptionNode)  this.descriptionNode.setStyle("display", "block");
                    }.bind(this)
                });
                this.calendar.show();
            }.bind(this));
        }else{
            this.node.getFirst().focus();
        }
	}
}); 