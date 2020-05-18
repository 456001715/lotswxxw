function oneanimateBackward(){
	$('#topic').replaceClassTo("topic-backward");
	$('#subtitle').replaceClassTo("subtitle-backward");
	$('#rdohtml').replaceClassTo("rdohtml-backward");
	$('#rdocss').replaceClassTo("rdocss-backward");
	$('#rdojs').replaceClassTo("rdojs-backward");
	$('#rdojyj').replaceClassTo("rdojyj-backward");
	
	$('#txt-up').val('oneanimate');
	$('#txt-down').val('oneanimateBackward');
	$('#down').replaceClassTo("animate-show");
}
function oneanimate(){
	//从2跳回来，或者从1跳过来
	if($('#boxplug').attr("class")=="boxplug-forward"||$('#boxplug').attr("class")=="boxplug-down"){
		$('#boxplug').replaceClassTo("boxplug-backward");
		$('#topic').replaceClassTo("topic-down");
		initplugs();
	}else{
		$('#topic').replaceClassTo("topic-forward");
		$('#subtitle').replaceClassTo("subtitle-forward");
		
		
	}
	
	//加载圆
	$('#rdohtml').replaceClassTo("rdohtml-forward");
	$('#rdocss').replaceClassTo("rdocss-forward");
	$('#rdojs').replaceClassTo("rdojs-forward");
	$('#rdojyj').replaceClassTo("rdojyj-forward");
	
	$('#txt-up').val('oneanimate');
	$('#txt-down').val('oneanimateBackward');
	$('#down').replaceClassTo("animate-hide");
	
	
}

$("#welcome").swipeToUp({fun:swipeToUp});
$("#welcome").swipeToDown({fun:swipeToDown});
function swipeToUp(){
	var upStr=$('#txt-up').val().toString();
	$phone.runFunction(eval(upStr));
}
function swipeToDown(){
	var downStr=$('#txt-down').val().toString();
	$phone.runFunction(eval(downStr));
}

function closewelcome(){
	A.Controller.section('#follow_section');
	//A.Controller.close&&A.Controller.close();
}
$("#welcome").mouseToUp({fun:swipeToUp});
$("#welcome").mouseToDown({fun:swipeToDown});
function mouseToUp(){
    var upStr=$('#txt-up').val().toString();
    $phone.runFunction(eval(upStr));
}
function mouseToDown(){
    var downStr=$('#txt-down').val().toString();
    $phone.runFunction(eval(downStr));
}
$(document).ready(function(){
	$("#content").css("height",$phone.bodyHeight()+"px");
});
