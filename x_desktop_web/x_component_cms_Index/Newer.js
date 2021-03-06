MWF.xDesktop.requireApp("Template", "Explorer", null, false);
MWF.xApplication.cms = MWF.xApplication.cms || {};
MWF.xApplication.cms.Index = MWF.xApplication.cms.Index || {};
MWF.xDesktop.requireApp("cms.Index", "Actions.RestActions", null, false);
MWF.require("MWF.xAction.org.express.RestActions", null, false);
MWF.require("MWF.widget.Mask", null, false);
MWF.xApplication.cms.Index.Newer = new Class({
    Extends: MWF.xApplication.Template.Explorer.PopupForm,
    Implements: [Options, Events],
    options: {
        "style": "default",
        "popupStyle" : "o2platform",
        "width": "850",
        "height": "510",
        "hasTop": true,
        "hasIcon": false,
        "hasTopContent" : true,
        "hasBottom": false,
        //"title": MWF.xApplication.cms.Index.LP.createDocument,
        "draggable": true,
        "closeAction": true,

        "ignoreDrafted" : false,
        "selectColumnEnable" : true,
        "restrictToColumn" : false
    },
    initialize: function (columnData, categoryData, app, view, options) {

        this.path = "/x_component_cms_Index/$Newer/";
        this.cssPath = "/x_component_cms_Index/$Newer/"+this.options.style+"/css.wcss";
        this._loadCss();

        MWF.xDesktop.requireApp("cms.Index", "$Newer.lp."+MWF.language, null, false);
        this.lp = MWF.xApplication.cms.Index.Newer.lp;

        this.options.title = this.lp.createDocument;

        this.setOptions(options);

        this.columnData = columnData;
        this.categoryData = categoryData;
        this.app = app;
        this.view = view;
        this.container = this.app.content;

        this.documentAction = new MWF.xApplication.cms.Index.Actions.RestActions();
        this.orgAction = new MWF.xAction.org.express.RestActions();
    },
    load : function(){
        if( !this.categoryData ){
            this._load();
            this.fireEvent( "postLoad" );
        }else if( this.options.ignoreDrafted ){
            this._load();
            this.fireEvent( "postLoad" );
        }else if(this.categoryData.workflowAppId && this.categoryData.workflowFlag ){
            this._load();
            this.fireEvent( "postLoad" );
        }else{
            var fielter = {
                "categoryIdList": [this.categoryData.id ],
                "creatorList": [layout.desktop.session.user.name]
            };
            this.documentAction.listDraftNext("(0)", 1, fielter, function(json){
                if( json.data.length > 0 ){
                    this._openDocument(json.data[0].id);
                    this.fireEvent( "postLoad" );
                }else{
                    this._load();
                    this.fireEvent( "postLoad" );
                }
            }.bind(this));
        }
    },
    _load : function(){
        this.isNew = true;
        this.isEdited = true;
        this._open();

        if( this.options.selectColumnEnable ){
            this.openSel();
        }
    },
    openSel : function(){
        this.formTopTextNode.set("text", this.lp.selCategory);
        if( this.sel ){
            this.sel.load();
        }else{
            this.sel = new MWF.xApplication.cms.Index.Newer.CategorySel(this.app, this.formContentNode, this, this.columnData, this.categoryData, {
                restrictToColumn : this.options.restrictToColumn
            });
            this.sel.load();
        }
    },
    _loadCss: function(){
        var key = encodeURIComponent(this.cssPath);
        if (MWF.widget.css[key]){
            this.css = MWF.widget.css[key];
        }else{
            this.cssPath = (this.cssPath.indexOf("?")!=-1) ? this.cssPath+"&v="+COMMON.version : this.cssPath+"?v="+COMMON.version;
            var r = new Request.JSON({
                url: this.cssPath,
                secure: false,
                async: false,
                method: "get",
                noCache: false,
                onSuccess: function(responseJSON, responseText){
                    this.css = responseJSON;
                    MWF.widget.css[key] = responseJSON;
                }.bind(this),
                onError: function(text, error){
                    alert(error + text);
                }
            });
            r.send();
        }
    },
    _createTableContent: function () {

        var categoryName = this.categoryData ? ( this.categoryData.name || this.categoryData.categoryName ) : this.lp.selectCategory;
        var html = ""
        if( this.options.selectColumnEnable ){

            this.selectArea = new Element("div",{styles:this.css.selectArea}).inject( this.formTableArea );
            this.selectContainer = new Element("div",{styles:this.css.selectContainer}).inject( this.selectArea );

            //html = "<table width='100%' height='90%' border='0' cellPadding='0' cellSpacing='0'>" +
            //"<tr>" +
            //"<td style='height: 40px; line-height: 40px; text-align: left; width: 40%' id='form_startColumn'></td>" +
            //"<td style='text-align: left;' id='form_startCategory'></td>" +
            //"</tr>" +
            //"</table>"
            html = "<div id='form_startColumn' style='float:left'></div><div id='form_startCategory' style='float:left'></div>"
            this.selectContainer.set("html", html);

            this.setSelectContent();
        }

        this.inputContainer = new Element("div",{styles:this.css.inputContainer}).inject( this.formTableArea );
        html = "<table width='100%' height='90%' border='0' cellPadding='0' cellSpacing='0'; >" +
            "<tr><td colSpan='2' style='height: 60px; line-height: 60px; text-align: center; font-size: 24px; ' id='form_startTitle'>" +
            this.lp.start+" - "+categoryName+"</td></tr>" +
            "<tr><td style='height: 38px; line-height: 38px; text-align: left; font-size:16px;color:#333'>"+this.lp.department+"：</td>" +
            "<td style='; text-align: left;' id='form_startDepartment'></td></tr>" +
            "<tr><td style='height: 38px; line-height: 38px;  text-align: left; font-size:16px;color:#333'>"+this.lp.identity+"：</td>" +
            "<td style='; text-align: left;'><div id='form_startIdentity'></div></td></tr>" +
            "<tr><td style='height: 38px; line-height: 38px;  text-align: left; font-size:16px;color:#333'>"+this.lp.date+"：</td>" +
            "<td style='; text-align: left;'><div id='form_startDate'></div></td></tr>" +
            "<tr><td style='height: 38px; line-height: 38px;  text-align: left; font-size:16px;color:#333'>"+this.lp.subject+"：</td>" +
            "<td style='; text-align: left;'><input type='text' id='form_startSubject' " +
            "style='width: 99%; border:1px solid #999; background-color:#FFF; border-radius: 3px; box-shadow: 0px 0px 6px #CCC; " +
            "height: 26px;'/></td></tr>" +
            "<tr><td style='height: 38px; line-height: 38px; text-align: left; font-size:16px;color:#333'></td>" +
            "<td style='text-align: left;' id='form_startAction'></td></tr>" +
            "</table>";
        this.inputContainer.set("html", html);

        this.setStartFormContent();

        this.startActionContainer = this.inputContainer.getElementById("form_startAction");
        this.startTitleNode = this.inputContainer.getElementById("form_startTitle");

        this.startOkActionNode = new Element("div", {
            "styles": this.css.startOkActionNode,
            "text": this.lp.ok
        }).inject(this.startActionContainer);

        this.cancelActionNode = new Element("div", {
            "styles": this.css.cancelActionNode,
            "text": this.lp.cancel
        }).inject(this.startActionContainer);

        this.cancelActionNode.addEvent("click", function(e){
            this.cancelStart(e);
        }.bind(this));
        this.startOkActionNode.addEvent("click", function(e){
            this.okStart(e);
        }.bind(this));
    },
    setSelectContent: function(){
        this.columnContainer = this.selectContainer.getElementById("form_startColumn");
        this.columnContainer.setStyles( this.css.columnContainer );
        this.selectContainer.addEvents({
           mouseover : function(){
               this.columnSelectNode.setStyles( this.css.columnSelectNode_over );
           }.bind(this),
            mouseout : function(){
                this.columnSelectNode.setStyles( this.css.columnSelectNode );
            }.bind(this),
            click : function(){
                this.openSel();
            }.bind(this)
        });

        this.columnIconNode = new Element("img", {styles : this.css.columnIconNode }).inject( this.columnContainer );
        if( this.columnData ){
            if (this.columnData.appIcon){
                this.columnIconNode.set("src", "data:image/png;base64,"+this.columnData.appIcon+"");
            }else{
                this.columnIconNode.set("src", "/x_component_cms_Index/$Main/default/icon/column.png");
            }
        }else{
            this.columnIconNode.set("src","/x_component_cms_Index/$Main/default/icon/all_40.png");
        }

        this.columnTextNode = new Element("div", {
            styles : this.css.columnTextNode,
            text : this.lp.all
        } ).inject( this.columnContainer );

        this.columnSelectNode = new Element("div", {styles : this.css.columnSelectNode }).inject( this.columnContainer );

        this.categoryContainer = this.selectContainer.getElementById("form_startCategory");
        this.categoryContainer.setStyles( this.css.categoryContainer );

        this.categoryTextNode = new Element("div", {
            styles : this.css.categoryTextNode,
            text : this.lp.clickForSelect
        }).inject( this.categoryContainer );
    },
    setCurrentColumn: function( column ){
        if( this.currentColumn  && this.currentColumn != column ){
            this.currentColumn.node.setStyles( this.css.columnItemNode );
            this.currentColumn.options.isCurrent = false;
        }
        this.currentColumn = column;
    },
    setCurrentCategory: function( category ){
        if( this.currentCategory && this.currentCategory != category ){
            this.currentCategory.node.setStyles( this.css.categoryItemNode );
            this.currentCategory.options.isCurrent = false;
        }
        this.currentCategory = category;

        var fielter = {
            "categoryIdList": [ category.data.id ],
            "creatorList": [layout.desktop.session.user.name]
        };
        this.documentAction.listDraftNext("(0)", 1, fielter, function(j){
            if( j.data && j.data.length > 0 ){
                this._openDocument(j.data[0].id);
                this.close();
            }else{
                this.documentAction.getColumn( {id : category.data.appId} , function( json ){
                    this.columnData = json.data;
                    if (this.columnData.appIcon){
                        this.columnIconNode.set("src", "data:image/png;base64,"+this.columnData.appIcon+"");
                    }else{
                        this.columnIconNode.set("src", "/x_component_cms_Index/$Main/default/icon/column.png");
                    }
                    this.columnTextNode.set("text", this.columnData.appName);

                    this.formTopTextNode.set("text", this.lp.createDocument);
                    this.categoryData = category.data;
                    this.categoryTextNode.set("text", this.categoryData.categoryName);
                    this.startTitleNode.set("text",this.lp.start+" - "+this.categoryData.categoryName);
                    this.sel.closeArea();

                }.bind(this));
            }
        }.bind(this));
    },
    setStartFormContent: function(){
        this.dateArea = this.formTableArea.getElementById("form_startDate");
        var d = new Date();
        this.dateArea.set("text", d.format("%Y-%m-%d %H:%M"));

        this.departmentSelArea = this.formTableArea.getElementById("form_startDepartment");
        this.identityArea = this.formTableArea.getElementById("form_startIdentity");
        this.subjectInput = this.formTableArea.getElementById("form_startSubject");

        this.loadDepartments();
    },
    loadDepartments: function(){
        if (this.app.desktop.session.user.name){
            this.orgAction.listIdentityByPerson(function(json){
                var selected = (json.data.length==1) ? true : false;
                json.data.each(function(id){
                    var departSel = new MWF.xApplication.cms.Index.Newer.DepartmentSel(id, this, this.departmentSelArea, this.identityArea);
                    if (selected) departSel.selected();
                }.bind(this));
            }.bind(this), null, this.app.desktop.session.user.name)
        }
    },
    cancelStart: function(e){
        var _self = this;
        if (this.subjectInput.get("value")){
            this.app.confirm("warn", e, this.lp.start_cancel_title, this.lp.start_cancel, "320px", "100px", function(){
                _self.close();
                this.close();
            },function(){
                this.close();
            }, null, this.app.content);
        }else{
            this.close();
        }
    },
    okStart: function(){
        if( !this.categoryData ){
            this.app.notice(this.lp.selectCategory, "error");
        }else{
            if( this.categoryData.workflowAppId && this.categoryData.workflowFlag ){
                this._createProcessDocument();
            }else{
                this._createDocument();
            }
        }
    },
    _createDocument: function(e){
        var title = this.subjectInput.get("value");
        var data = {
            "id" : this.documentAction.getUUID(),
            "isNewDocument" : true,
            "title": title,
            "creatorIdentity": this.identityArea.get("value"),
            "appId" :this.categoryData.appId,
            "categoryId" : this.categoryData.id,
            "form" : this.categoryData.formId,
            "formName" :this.categoryData.formName,
            "docStatus" : "draft",
            "categoryName" : this.categoryData.name,
            "categoryAlias" : this.categoryData.alias,
            "attachmentList" : []
        };

        if (!data.title){
            this.subjectInput.setStyle("border-color", "red");
            this.subjectInput.focus();
            this.app.notice(this.lp.inputSubject, "error");
        }else if (!data.creatorIdentity){
            this.departmentSelArea.setStyle("border-color", "red");
            this.app.notice(this.lp.selectStartId, "error");
        }else{
            this.mask = new MWF.widget.Mask({"style": "desktop"});
            this.mask.loadNode(this.formAreaNode);
            this.documentAction.addDocument( data, function(json){
                this.mask.hide();

                //this.markNode.destroy();
                this.close();

                this._openDocument( json.data.id );
                //this.fireEvent("started", [json.data, title, this.categoryData.name]);

                //this.app.refreshAll();
                this.app.notice(this.lp.Started, "success");
                //    this.app.processConfig();
            }.bind(this), null);
        }
    },
    _openDocument: function(id,el){
        var _self = this;

        var appId = "cms.Document"+id;
        if (_self.app.desktop.apps[appId]){
            _self.app.desktop.apps[appId].setCurrent();
        }else {
            var options = {
                "readonly" :false,
                "documentId": id,
                "appId": appId,
                "postPublish" : function(){
                    //if(_self.creater.view )_self.creater.view.reload();
                    this.fireEvent( "postPublish" );
                }.bind(this)
            };
            this.app.desktop.openApplication(el, "cms.Document", options);
        }
    },

    _createProcessDocument:function(e){
        var title = this.subjectInput.get("value");
        var processId = this.categoryData.workflowFlag;
        var data = {
            "title":this.subjectInput.get("value"),
            "identity": this.identityArea.get("value")
        };
        if (!data.title){
            this.subjectInput.setStyle("border-color", "red");
            this.subjectInput.focus();
            this.app.notice(this.lp.inputSubject, "error");
        }else if (!data.identity){
            this.departmentSelArea.setStyle("border-color", "red");
            this.app.notice(this.lp.selectStartId, "error");
        }else{
            var workData = {
                cmsDocument : {
                    "isNewDocument" : true,
                    "title": title,
                    "creatorIdentity": data.identity,
                    "appId" :this.categoryData.appId,
                    "categoryId" : this.categoryData.id,
                    //"form" : this.categoryData.formId,
                    //"formName" :this.categoryData.formName,
                    "docStatus" : "draft",
                    "categoryName" : this.categoryData.name,
                    "categoryAlias" : this.categoryData.alias,
                    "createTime": new Date().format("db"),
                    "attachmentList" : []
                }
            };

            this.mask = new MWF.widget.Mask({"style": "desktop"});
            this.mask.loadNode(this.formAreaNode);
            this.documentAction.startWork( function( json ){
                this.mask.hide();

                //this.markNode.destroy();
                this.close();

                this.afterStartProcess( json.data, data.title, this.categoryData.workflowName, workData );
                //this.fireEvent("started", [json.data, title, this.categoryData.name]);

                //this.app.refreshAll();
                this.app.notice(this.lp.Started, "success");
            }.bind(this), null, processId, data)
        }
    },
    afterStartProcess: function(data, title, processName, workData){
        var workInfors = [];
        var currentTask = [];

        data.each(function(work){
            if (work.currentTaskIndex != -1) currentTask.push(work.taskList[work.currentTaskIndex].work);
            workInfors.push(this.getStartWorkInforObj(work));
        }.bind(this));
        var workId = currentTask[0];

        this.documentAction.saveWorkData(function(){

            if (currentTask.length==1){
                var options = {"workId": workId};
                this.app.desktop.openApplication(null, "process.Work", options);

                this.createStartWorkResault(workInfors, title, processName, false);
            }else{
                this.createStartWorkResault(workInfors, title, processName, true);
            }

        }.bind(this), null, workId, workData)


    },
    getStartWorkInforObj: function(work){
        var users = [];
        var currentTask = "";
        work.taskList.each(function(task, idx){
            users.push(task.person+"("+task.department + ")");
            if (work.currentTaskIndex==idx) currentTask = task.id;
        }.bind(this));
        return {"activity": work.fromActivityName, "users": users, "currentTask": currentTask};
    },
    createStartWorkResault: function(workInfors, title, processName, isopen){
        var content = "";
        workInfors.each(function(infor){
            content += "<div><b>"+this.lp.nextActivity+"<font style='color: #ea621f'>"+infor.activity+"</font>, "+this.lp.nextUser+"<font style='color: #ea621f'>"+infor.users.join(", ")+"</font></b>"
            if (infor.currentTask && isopen){
                content += "&nbsp;&nbsp;&nbsp;&nbsp;<span value='"+infor.currentTask+"'>"+this.lp.deal+"</span></div>";
            }else{
                content += "</div>";
            }
        }.bind(this));

        var msg = {
            "subject": this.lp.processStarted,
            "content": "<div>"+this.lp.processStartedMessage+"“["+processName+"]"+title+"”</div>"+content
        };
        var tooltip = layout.desktop.message.addTooltip(msg);
        var item = layout.desktop.message.addMessage(msg);

        this.setStartWorkResaultAction(tooltip);
        this.setStartWorkResaultAction(item);
    },
    setStartWorkResaultAction: function(item){
        var node = item.node.getElements("span");
        node.setStyles(this.css.dealStartedWorkAction);
        var _self = this;
        node.addEvent("click", function(e){
            var options = {"taskId": this.get("value")};
            _self.app.desktop.openApplication(e, "process.Work", options);
        });
    }

});


MWF.xApplication.cms.Index.Newer.DepartmentSel = new Class({
    initialize: function(data, starter, container, idArea){
        this.data = data;
        this.starter = starter;
        this.container = container;
        this.idArea = idArea;
        this.css = this.starter.css;
        this.isSelected = false;
        this.load();
    },
    load: function(){

        this.node = new Element("div", {"styles": this.css.departSelNode}).inject(this.container);
        this.starter.orgAction.getDepartmentByIdentity(function(department){
            this.node.set("text", department.data.name);
        }.bind(this), null, this.data.name);

        this.node.addEvents({
            "mouseover": function(){if (!this.isSelected) this.node.setStyles(this.css.departSelNode_over);}.bind(this),
            "mouseout": function(){if (!this.isSelected) this.node.setStyles(this.css.departSelNode_out);}.bind(this),
            "click": function(){
                this.selected();
            }.bind(this),
        });
    },
    selected: function(){
        if (!this.isSelected){
            if (this.starter.currentDepartment) this.starter.currentDepartment.unSelected();
            this.node.setStyles(this.css.departSelNode_selected);
            this.isSelected = true;
            this.starter.currentDepartment = this;

            this.idArea.set({
                "text": this.data.display,
                "value": this.data.name
            });
        }
    },
    unSelected: function(){
        if (this.isSelected){
            if (this.starter.currentDepartment) this.starter.currentDepartment = null;
            this.node.setStyles(this.css.departSelNode);
            this.isSelected = false;
        }
    }

});



MWF.xApplication.cms.Index.Newer.CategorySel = new Class({
    Extends: MWF.widget.Common,
    Implements: [Options, Events],
    options: {
        "style": "default",
        "restrictToColumn" : false
    },
    initialize: function(app, node, newer, columnData, categoryData, options){
        this.setOptions(options);
        this.node = node;
        this.newer = newer;
        this.lp = newer.lp;
        this.css = newer.css;
        this.action = newer.documentAction;

        this.columnData = columnData;
        this.categoryData = categoryData;
    },
    load: function(){
        if (!this.areaNode){
            this.createArea();
        }
        this.areaNode.fade("1");
    },
    closeArea: function(){
        if (this.areaNode) this.areaNode.fade("out");
    },

    createArea: function(){
        this.areaNode = new Element("div.categorySelAreaNode", {"styles": this.css.categorySelAreaNode}).inject(this.node );
        this.areaNode.addEvent("click", function(e){
            //this.closeArea();
        }.bind(this));

        this.columnContainer = new Element("div", {"styles": this.css.selColumnAreaNode}).inject( this.areaNode );
        this.columnScrollNode = new Element("div.columnScrollNode", {"styles": this.css.selColumnScrollNode}).inject(this.columnContainer);
        this.setScrollBar( this.columnScrollNode )
        this.columnContentNode = new Element("div.selColumnContentNode", {"styles": this.css.selColumnContentNode}).inject(this.columnScrollNode);

        this.categoryContainer = new Element("div", {"styles": this.css.selCategoryAreaNode}).inject( this.areaNode );
        this.categoryScrollNode = new Element("div", {"styles": this.css.selCategoryScrollNode}).inject(this.categoryContainer);
        this.setScrollBar( this.categoryScrollNode )
        this.categoryContentNode = new Element("div", {"styles": this.css.selCategoryContentNode}).inject(this.categoryScrollNode);

        if( this.options.restrictToColumn && this.columnData ){
            new MWF.xApplication.cms.Index.Newer.CategorySel.Column(this.columnData, this.app, this.newer, this.columnContentNode, this.categoryContentNode, {
                "needGetCategorys": true,
                "isCurrent": true,
                "currentCategory" : this.categoryData ? this.categoryData.id : "",
                "restrictToColumn" : this.options.restrictToColumn
            });
        }else{
            this.listColumns();
        }

    },
    listColumns: function(){
        var c = { wrapOutCategoryList : [] };
        this.action.listColumnByPublish(function(json){
            json.data.each(function(column){
                if(!column.name)column.name = column.appName;
                if( column.wrapOutCategoryList && column.wrapOutCategoryList.length ){
                    column.wrapOutCategoryList.each(function(category){
                        c.wrapOutCategoryList.push(category);
                    }.bind(this));
                }
            }.bind(this));
            new MWF.xApplication.cms.Index.Newer.CategorySel.Column(c, this.app, this.newer, this.columnContentNode, this.categoryContentNode, {
                "needGetCategorys": false,
                "isAll" : true,
                "isCurrent" : this.columnData ? false : true,
                "currentCategory" : this.categoryData ? this.categoryData.id : "",
                "restrictToColumn" : this.options.restrictToColumn
            });
            json.data.each( function(column) {
                if(!column.name)column.name = column.appName;
                new MWF.xApplication.cms.Index.Newer.CategorySel.Column(column, this.app, this.newer, this.columnContentNode, this.categoryContentNode, {
                    "needGetCategorys": false,
                    "isCurrent": ( this.columnData && this.columnData.id == column.id) ? true : false,
                    "currentCategory" : this.categoryData ? this.categoryData.id : "",
                    "restrictToColumn" : this.options.restrictToColumn
                });
            }.bind(this) )
        }.bind(this));
    }
});

MWF.xApplication.cms.Index.Newer.CategorySel.Column = new Class({
    Implements: [Options],
    options: {
        "needGetCategorys": false,
        "isAll" : false,
        "isCurrent" : false,
        "currentCategory" : "",
        "restrictToColumn" : false
    },
    initialize: function(data, app, newer, container, categoryContainer, options ){
        this.setOptions( options );
        this.data = data;
        this.app = app;
        this.newer = newer;
        this.lp = newer.lp;
        this.css = newer.css;
        this.action = newer.documentAction;

        this.container = container;
        this.categoryContainer = categoryContainer;

        this.load();
    },
    load: function(){
        this.node = new Element("div", {"styles": this.css.columnItemNode}).inject(this.container);

        var iconNode = this.iconNode = new Element("img",{
            "styles" : this.css.columnItemIconNode
        }).inject(this.node);
        if( this.options.isAll ){
            this.iconNode.set("src", "/x_component_cms_Index/$Main/default/icon/all_40.png")
        }else if (this.data.appIcon){
            this.iconNode.set("src", "data:image/png;base64,"+this.data.appIcon+"");
        }else{
            this.iconNode.set("src", "/x_component_cms_Index/$Main/default/icon/column.png")
        }

        this.textNode = new Element("div", {"styles": this.css.columnItemTextNode}).inject(this.node);
        if( this.options.isAll ){
            this.textNode.set("text", this.lp.all);
        }else{
            this.textNode.set("text", (this.data.name || this.data.appName) );
        }

        if( this.options.isAll ){
           //new Element("div", {"styles": this.css.columnSelectNode}).inject(this.node);
        }

        this.node.addEvents({
            "mouseover" : function(){ if( !this.options.isCurrent )this.node.setStyles( this.css.columnItemNode_over ) }.bind(this),
            "mouseout" : function(){ if( !this.options.isCurrent )this.node.setStyles( this.css.columnItemNode ) }.bind(this),
            "click" : function(){ this.setCurrent(); }.bind(this)
        });

        if( this.options.isCurrent )this.setCurrent();
    },
    setCurrent: function(){
        this.options.isCurrent = true;
        this.node.setStyles( this.css.columnItemNode_current );
        this.newer.setCurrentColumn( this );
        this.loadCategory();
    },
    loadCategory: function(){
        this.categoryContainer.empty();
        if( this.options.needGetCategorys ){
            this.action.listCategoryByPublisher(this.data.id,function(json){
                if (json.data.length){
                    var isSetCurrentImmediately = ( json.data.length == 1 && ( this.options.restrictToColumn || this.options.isAll  ) );
                    json.data.each(function(category){
                        new MWF.xApplication.cms.Index.Newer.CategorySel.Category(category, this, this.categoryContainer, {
                            isCurrent : ( this.options.currentCategory == category.id ) || isSetCurrentImmediately
                        });
                    }.bind(this));
                }else{
                    this.node.setStyle("display", "none");
                }
            }.bind(this), null, this.data.id);
        }else{
            if( this.data.wrapOutCategoryList && this.data.wrapOutCategoryList.length ){
                var isSetCurrentImmediately = ( this.data.wrapOutCategoryList.length == 1 && ( this.options.restrictToColumn || this.options.isAll  ) );
                this.data.wrapOutCategoryList.each(function(category){
                    new MWF.xApplication.cms.Index.Newer.CategorySel.Category(category, this, this.categoryContainer,{
                        isCurrent : ( this.options.currentCategory == category.id ) || isSetCurrentImmediately
                    });
                }.bind(this));
            }else{
                this.node.setStyle("display", "none");
            }
        }
    }
});

MWF.xApplication.cms.Index.Newer.CategorySel.Category = new Class({
    Implements: [Options],
    options: {
        "isCurrent" : false
    },
    initialize: function(data, column, container, options){
        this.setOptions( options );
        this.data = data;
        this.column = column;
        this.app = this.column.app;
        this.newer = this.column.newer;
        this.container = container;
        this.css = this.newer.css;

        this.load();
    },
    load: function(){
        if( !this.data.name )this.data.name = this.data.categoryName;
        this.node = new Element("div.categoryItem", {"styles": this.css.categoryItemNode}).inject(this.container);
        if( this.options.isCurrent )this.node.setStyles( this.css.categoryItemNode_over );

        this.textNode = new Element("div", {"styles": this.css.categoryItemTextNode}).inject(this.node);
        this.textNode.set({
            "text": this.data.categoryName
        });
        var _self = this;
        this.node.addEvents({
            "mouseover": function(e){if( !this.options.isCurrent )this.node.setStyles(this.css.categoryItemNode_over); }.bind(this),
            "mouseout": function(e){if( !this.options.isCurrent )this.node.setStyles(this.css.categoryItemNode);}.bind(this),
            "click": function(e){
                this.setCurrent();
            }.bind(this)
        });
        if( this.options.isCurrent )this.setCurrent();
    },
    setCurrent: function(){
        this.options.isCurrent = true;
        this.node.setStyles( this.css.categoryItemNode_current );
        this.newer.setCurrentCategory( this );
    }
});