<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ taglib uri="/WEB-INF/tld/cardeploy.tld" prefix="car"%>
<%@ taglib uri="/WEB-INF/tld/carphoto.tld" prefix="car_ph"%>
<%@ taglib uri="/WEB-INF/tld/cardescription.tld" prefix="car_desc"%>
<%@ taglib uri="/WEB-INF/tld/carname.tld" prefix="car_name"%>
<%@ taglib uri="/WEB-INF/tld/carprice.tld" prefix="car_price"%>
<%@ taglib uri="/WEB-INF/tld/carid.tld" prefix="car_id"%>

<html>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="dropdown offset-3">
  <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
    Dropdown
  </button>
  <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
    <button class="dropdown-item" type="button">Action</button>
    <button class="dropdown-item" type="button">Another action</button>
    <button class="dropdown-item" type="button">Something else here</button>
  </div>
</div>
<br>
<form action="controller" method="post">
 <car:deploy>
 <car_ph:photo>"http://images.vfl.ru/ii/1613014509/ace96fdb/33290231.jpg"</car_ph:photo>
 <car_name:name>${notSorted[1].brand}&ensp;${notSorted[1].model}</car_name:name>
 <h7>Start date</h7>
 <input id="${notSorted[1].id}" type="date" class="form-control" name="${notSorted[1].id}rentFrom" placeholder="" />
 <h7>End date</h7>
<br><input id="${notSorted[1].id}+1" type="date" class="form-control" name="${notSorted[1].id}rentTo" placeholder="" />
<br>

<input type = "hidden" name="command" value="order"/>


 <car_desc:desc></car_desc:desc>
 <car_price:price>Price:<h4>${notSorted[k].price}uah/d</h4></car_price:price>
 <button type="submit" name="carTotal" class="btn btn-warning mt-4 text-white" value="${notSorted[1].id}"><i class="icon-cart-add mr-2"></i> Add to cart</button>
 </div>
 </car:deploy>
 </div>
 </div>
 <input type = "text" name="numbOfDrivers1" />
 <input type = "number" name="numbOfCars1" />
 </form>