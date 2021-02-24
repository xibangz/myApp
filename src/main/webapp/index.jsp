<%@ include file="WEB-INF/jspf/page.jspf" %>
<%@ include file="WEB-INF/jspf/taglib.jspf" %>
<html>
<%@ include file="WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="WEB-INF/jspf/header.jspf" %>
<main role="main">

     <section class="jumbotron text-center">
        <div class="container">
          <h1 class="jumbotron-heading">Car rental</h1>
          <p class="lead text-muted">Your ad could be here .</p>
          <p>
          <c:if test="${empty user}">
          <div class="row ">
            <div class="offset-4">&ensp;&ensp;
            <a href="login.jsp" class="btn btn-success btn-lg active" role="button" aria-pressed="true">Sing in</a>
            </div>
            <div class="offset-1">
            <a href="registration.jsp" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Registration</a>
            </div>
            </div>
            </c:if>
          </p>
        </div>
      </section>
</form>


      <div class="album py-5 ">
        <div class="container">
          <div class="row">
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
              <c:if test="${empty user}">
              <a id="fake" href="login.jsp">
              </c:if>
              <c:if test="${not empty user}">
              <a id="fake" href="controller?command=carsList&refresh=true">
              </c:if>
               <img src="http://images.vfl.ru/ii/1613013776/e56ced27/33290203.jpg" alt="..." class="img-thumbnail">
                <div class="card-body">

                  <div class="d-flex justify-content-between align-items-center">
                  <h3>Rent this car</h3>
                  <small class="text-muted">Best offer</small>
                     </div>
                </div>
                </a>
              </div>
            </div>
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
             <c:if test="${empty user}">
             <a id="fake" href="login.jsp">
             </c:if>
             <c:if test="${not empty user}">
             <a id="fake" href="controller?command=carsList&refresh=true">
             </c:if>
                <img src="http://images.vfl.ru/ii/1613013831/42fb37bb/33290205.jpg" alt="..." class="img-thumbnail">
                <div class="card-body">

                  <div class="d-flex justify-content-between align-items-center">

                      <h3>Rent this car</h3>
                      <small class="text-muted">Best offer</small>

                  </div>
                </div>
                </a>
              </div>
            </div>
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
               <c:if test="${empty user}">
               <a id="fake" href="login.jsp">
               </c:if>
               <c:if test="${not empty user}">
               <a id="fake" href="controller?command=carsList&refresh=true">
               </c:if>
                <img src="http://images.vfl.ru/ii/1613014508/67f7113e/33290229.jpg" alt="..." class="img-thumbnail">
                <div class="card-body">

                  <div class="d-flex justify-content-between align-items-center">

                      <h3>Rent this car</h3>

                   <small class="text-muted">Best offer</small>
                  </div>
                </div>
                </a>
              </div>
            </div>

            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
               <c:if test="${empty user}">
               <a id="fake" href="login.jsp">
               </c:if>
               <c:if test="${not empty user}">
               <a id="fake" href="controller?command=carsList&refresh=true">
               </c:if>
                <img src="http://images.vfl.ru/ii/1613014509/86031b58/33290230.jpg" alt="..." class="img-thumbnail">
                <div class="card-body">

                  <div class="d-flex justify-content-between align-items-center">

                       <h3>Rent this car</h3>

                   <small class="text-muted">Best offer</small>
                  </div>
                </div>
                </a>
              </div>
            </div>
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
               <c:if test="${empty user}">
               <a id="fake" href="login.jsp">
               </c:if>
               <c:if test="${not empty user}">
               <a id="fake" href="controller?command=carsList&refresh=true">
               </c:if>
               <img src="http://images.vfl.ru/ii/1613014509/ace96fdb/33290231.jpg" alt="..." class="img-thumbnail">
                <div class="card-body">

                  <div class="d-flex justify-content-between align-items-center">

                      <h3>Rent this car</h3>

                    <small class="text-muted">Best offer</small>
                  </div>
                </div>
                </a>
              </div>
            </div>
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
               <c:if test="${empty user}">
               <a id="fake" href="login.jsp">
               </c:if>
               <c:if test="${not empty user}">
               <a id="fake" href="controller?command=carsList&refresh=true">
               </c:if>
                <img src="http://images.vfl.ru/ii/1613014509/51da455b/33290232.jpg" alt="..." class="img-thumbnail">
                <div class="card-body">

                  <div class="d-flex justify-content-between align-items-center">

                      <h3>Rent this car</h3>

                    <small class="text-muted">Best offer</small>
                  </div>
                </div>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
      </form>
</main>
</body>
</html>
