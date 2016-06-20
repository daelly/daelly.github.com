<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/cms/static/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/cms/static/ckfinder/ckfinder.js"></script>
<!-- <script type="text/javascript" src="/cms/static/js/admin/admin.js"></script> -->
<script type="text/javascript">
	function commonUpload(callback){
		
		var finder = new CKFinder();
	//	finder.basePath = '../';	// The path for the installation of CKFinder (default = "/ckfinder/").
		
		finder.selectActionFunction = function(fileUrl,data){
			callback(fileUrl,data);
		};
		
		/* finder.callback = function( api )
		{
			CKFinder.dialog.add( 'mydialog', function( api )
			{
				// CKFinder.dialog.definition
				var dialogDefinition =
				{
					title : 'Sample dialog',
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
									type : 'html',
									html : '<h3>This is some sample HTML content.</h3>'
								},
								{
									type : 'textarea',
									id : 'textareaId',
									rows : 4,
									cols : 40
								}
							]
						}
					],
					buttons : [ CKFinder.dialog.cancelButton, CKFinder.dialog.okButton ]
				};

				return dialogDefinition;
			} );

			api.addFileContextMenuOption( { label : "Open dialog", command : "test" } , function( api, file )
			{
				api.openDialog('mydialog');
			});
		}; */
		
		//finder.extraPlugins = 'basketTest';
		finder.popup();
	}

	function callback(hrefUrl,data){
		debugger;
		$("#url").val(hrefUrl);
	}
	
</script>
</head>
<body>
	<input type="text" name="url" id="url" />
	<button type="button" onclick="commonUpload(callback)">上传</button>
</body>
</html>