// The root URL for the RESTful services
// var rootURL = "http://localhost/cellar/api/wines";

var rootXoURL = "http://localhost:8080/xo/xoSession";
var rootMerchantURL = "http://localhost:8080/xo/merchant/";

var merchantID = "9876";

var currentXoSession;

// Retrieve XO Session list when application starts
//findAll();

// Nothing to delete or update in initial application state
//$('#btnUpdate').hide();
//$('#btnDelete').hide();

//// Register listeners
//$('#btnSearch').click(function() {
//search($('#searchKey').val());
//return false;
//});
//
//// Trigger search when pressing 'Return' on search key input field
//$('#searchKey').keypress(function(e){
//if(e.which == 13) {
//search($('#searchKey').val());
//e.preventDefault();
//return false;
//    }
//});

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

//$('#wineList a').live('click', function() {
//findById($(this).data('identity'));
//});
//
//// Replace broken images with generic wine bottle
//$("img").error(function(){
//  $(this).attr("src", "pics/generic.jpg");
//
//});
//
//function search(searchKey) {
//if (searchKey == '')
//findAll();
//else
//findByName(searchKey);
//}

function newXoSession(merchantId) {
  $.ajax({
    url: rootMerchantURL + merchantId,
    type: "POST",
    dataType: "json",
   	success: renderXoSession
  });
}

//	 
//function newWine() {
//$('#btnDelete').hide();
//currentWine = {};
//renderDetails(currentWine); // Display empty form
//}
//
//function findAll() {
//console.log('findAll');
//$.ajax({
//type: 'GET',
//url: rootURL,
//dataType: "json", // data type of response
//success: renderList
//});
//}
//
//function findByName(searchKey) {
//console.log('findByName: ' + searchKey);
//$.ajax({
//type: 'GET',
//url: rootURL + '/search/' + searchKey,
//dataType: "json",
//success: renderList
//});
//}
//
//function findById(id) {
//console.log('findById: ' + id);
//$.ajax({
//type: 'GET',
//url: rootURL + '/' + id,
//dataType: "json",
//success: function(data){
//$('#btnDelete').show();
//console.log('findById success: ' + data.name);
//currentWine = data;
//renderDetails(currentWine);
//}
//});
//}

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

//function renderXOSession(data) {
//// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
//	var list = data == null ? [] : (data.wine instanceof Array ? data.wine : [data.wine]);
//	
//	$('#wineList li').remove();
//	$.each(list, function(index, wine) {
//	$('#wineList').append('<li><a href="#" data-identity="' + wine.id + '">'+wine.name+'</a></li>');
//	});
//}

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
