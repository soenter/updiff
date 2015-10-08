<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<ul class="pagination pagination-sm no-margin pull-right">
    <e:choose>
        <e:when test="${paging.startPage != 1}">
            <li><a href="JavaScript:prePage();">&laquo;</a></li>
        </e:when>
    </e:choose>
    <e:if test="${paging.pageSize > 0}">
        <e:forEach var="i" begin="1" end="${paging.pageSize}">
            <e:choose>
                <e:when test="${i == paging.startPage}">
                    <li><a href="JavaScript:;" style="background-color:lightgrey;">${i}</a></li>
                </e:when>
                <e:otherwise>
                    <li><a href="JavaScript:paging(${i});">${i}</a></li>
                </e:otherwise>
            </e:choose>
        </e:forEach>
    </e:if>
    <e:choose>
        <e:when test="${paging.startPage != paging.pageSize}">
            <li><a href="JavaScript:nextPage();">&raquo;</a></li>
        </e:when>
    </e:choose>
    <script>
        function prePage(){
            var startPageInput = $("#common-paging-startPage-id");
            var page = parseInt(startPageInput.val()) - 1;
            if(page > 0){
                paging(page);
            }
        }
        function paging(page){
            var startPageInput = $("#common-paging-startPage-id");
            startPageInput.val(page);
            startPageInput.closest('form').submit();
        }
        function nextPage(){
            var startPageInput = $("#common-paging-startPage-id");
            var page = parseInt(startPageInput.val()) + 1;
            paging(page);
        }
    </script>
</ul>
<input type="hidden" id="common-paging-startPage-id" name="paging.startPage" value="${paging.startPage}">