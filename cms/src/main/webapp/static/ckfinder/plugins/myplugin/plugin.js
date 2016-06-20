CKFinder.addPlugin( 'myplugin', {
	readOnly : false,
	connectorInitialized : function( api, xml ){
		//alert(xml);
	},
	uiReady : function( api ){
		var file,doc;
		var initJcrop = function(dialog){
			CKFinder.scriptLoader.load(["/cms/static/js/jquery.1.9.1min.js","/cms/static/lib/Jcrop-0.9.12/js/jquery.Jcrop.min.js"],function(){
				alert($("#previewImage").length);
			});
		};
		CKFinder.dialog.add( 'tailordialog', function( api )
		{
			// CKFinder.dialog.definition
			var dialogDefinition =
			{
				title : '裁剪图片',
				minWidth : 390,
				minHeight : 130,
				onOk : function() {
					// "this" is now a CKFinder.dialog object.
					// Accessing dialog elements:
					var textareaObj = this.getContentElement( 'tab1', 'textareaId' );
					alert( "You have entered: " + textareaObj.getValue() );

					// A shorter version:
					// alert( "You have entered: " + this.getValueOf( 'tab1', 'textareaId' ) );
				},
				onShow : function(){
					var dialog = this;
					//current selected pic
					file = api.getSelectedFile();
					var imgUrl = file.getUrl();
					doc = dialog.getElement().getDocument();
					var previewImg = doc.getById( 'previewImage' );
					previewImg.setAttribute( 'src', imgUrl);
				},
				contents : [
					{
						id : 'tab1',
						label : '',
						title : '',
						expand : true,
						padding : 0,
						elements :
						[
						 	{
						 		type : 'vbox',
						 		heights : ['20px','200px'],
						 		children :
						 		[
									{
										type : 'html',
										html : '<h5>选择裁剪区域</h5>',
										height : '30px',
									},
									{
										type : 'html',
										html : "<img id='previewImage' src='' style='width:350px;'/>"
									}
						 		]
						 	}
						]
					}
				],
				buttons : [ CKFinder.dialog.cancelButton, CKFinder.dialog.okButton ]
			};

			return dialogDefinition;
		} );

		api.addFileContextMenuOption( { label : "裁剪", command : "tailor" } , function( api, file )
		{
			if(!file.isImage()){
				alert("请选择图片裁剪!");
			}
			api.openDialog('tailordialog',function(a,b,c,d){
			});
		});
	}
});