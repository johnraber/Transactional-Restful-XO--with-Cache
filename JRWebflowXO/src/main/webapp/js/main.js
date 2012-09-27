// The root URL for the RESTful services
// var rootURL = "http://localhost/cellar/api/wines";

var rootXoURL = "http://localhost:8080/xo/xoSession";
var rootMerchantURL = "http://localhost:8080/xo/merchant/";

var merchantID = "9876";

var currentXoSession;

// Retrieve XO Session list when application starts
//findAll();

// Nothing to delete or update in initial application state//$('#btnUpdate').hide();
//$('#btnDelete').hide();


$('#btnAdd').click(function() {
  newXoSession(merchantID);
  return false;
});


$('#btnUpdate').click(function() {
  updateXoSession();
  return false;
});

$('#btnSave').click(function() {
  commitXoSession();
  return false;
});


$('#btnDelete').click(function() {
  deleteXoSession();
  return false;
});


function newXoSession(merchantId) {
  $.ajax({
    url: rootMerchantURL + merchantId,
    type: "POST",
    dataType: "json",
   	success: renderXoSession
  });
}


function commitXoSession() {
  console.log('commitXoSession');
  $.ajax({
    type: 'POST',
    contentType: 'application/json',
    url: rootXoURL + '/' + $('#xoSessionId').val(),
    dataType: "json",
    data: formToJSON(),
    success: clearForm
//success: function(data, textStatus, jqXHR){
//alert('Wine created successfully');
//$('#btnDelete').show();
//$('#wineId').val(data.id);
//},
//error: function(jqXHR, textStatus, errorThrown){
//alert('addWine error: ' + textStatus);
//}
  });
}

function updateXoSession() {
  console.log('updateXoSession');
  $.ajax({
    type: 'PUT',
    contentType: 'application/json',
    url: rootXoURL + '/' + $('#xoSessionId').val(),
    dataType: "json",
    data: formToJSON(),
    success: renderXoSession
    //function(data, textStatus, jqXHR){
//    	$('#btnUpdate').show();
//    	$('#btnDelete').show();	
//    	alert('XO Session updated successfully');
//  },
//    error: function(jqXHR, textStatus, errorThrown){
//    alert('updateXoSession error: ' + textStatus);
//    }
  });
}

function deleteXoSession() {
	console.log('deleteXoSession');
	$.ajax({
	type: 'DELETE',
	url: rootXoURL + '/' + $('#xoSessionId').val(),
	success: clearForm
//	success: function(data, textStatus, jqXHR){
//		alert('Wine deleted successfully');
//		},
//		error: function(jqXHR, textStatus, errorThrown){
//			alert('deleteWine error');
//		}
	});
}



function clearForm() {
	$('#xoSessionId').val('');
	$('#merchant').val('');
	$('#buyer').val('');
	$('#item').val('');
	$('#price').val('');
	$('#purchaseDate').val('');
	$('#shippingAddress').val('');
}


function renderXoSession(xoSession) {
	$('#xoSessionId').val(xoSession.xosessionId);
	$('#merchant').val(xoSession.merchant);
	$('#buyer').val(xoSession.buyer);
	$('#item').val(xoSession.item);
	$('#price').val(xoSession.price);
	$('#purchaseDate').val(xoSession.purchaseDate);
	$('#shippingAddress').val(xoSession.shippingAddress);
}

// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
	return JSON.stringify({
	"xosessionId": $('#xoSessionId').val(),
	"merchant": $('#merchant').val(),
	"buyer": $('#buyer').val(),
	"item": $('#item').val(),
	"price": $('#price').val(),
	"purchaseDate": $('#purchaseDate').val(),
	"shippingAddress": $('#shippingAddress').val()
	});
}
