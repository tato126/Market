// 'main' 객체 대신 'productFormHandler'와 같이 좀 더 명확한 이름을 사용할 수 있습니다.
var productFormHandler = {
    init : function () {
        var _this = this;
        // '#btn-save' 버튼 클릭 시 save 함수 호출
        $('#btn-save').on('click', function (event) {
            // 기본 폼 제출 방지 (AJAX를 사용하므로)
            event.preventDefault();
            _this.save();
        });
    },
    save : function () {
        var data = {
            // products-registry.mustache 템플릿의 폼 필드 ID에 맞춰서 값을 가져옵니다.
            productName: $('#productName').val(),
            sellerName: $('#sellerName').val(),
            description: $('#description').val(),
            price: $('#price').val(), // 문자열로 전송되지만, ProductRequestDto에서 BigDecimal로 변환됩니다.
            stockQuantity: $('#stockQuantity').val(), // 문자열로 전송되지만, ProductRequestDto에서 Integer로 변환됩니다.
            productState: $('#productState').val(),
            productCategory: $('#productCategory').val()
        };

        // 간단한 유효성 검사 (필요에 따라 확장)
        if (!data.productName || !data.sellerName || !data.price || !data.stockQuantity) {
            alert('필수 항목을 모두 입력해주세요: 상품명, 판매자 이름, 가격, 재고 수량.');
            $('#formResult').text('필수 항목을 모두 입력해주세요.').removeClass('success').addClass('error');
            return;
        }

        // 가격과 재고 수량이 숫자인지 확인 (선택 사항, 서버 측에서도 유효성 검사 필요)
        if (isNaN(parseFloat(data.price)) || isNaN(parseInt(data.stockQuantity))) {
            alert('가격과 재고 수량은 숫자여야 합니다.');
            $('#formResult').text('가격과 재고 수량은 숫자여야 합니다.').removeClass('success').addClass('error');
            return;
        }
        // ProductRequestDto 필드 타입에 맞게 명시적으로 변환 (선택 사항, Spring이 대부분 처리)
        // data.price = parseFloat(data.price);
        // data.stockQuantity = parseInt(data.stockQuantity);


        $.ajax({
            type: 'POST',
            url: '/api/products', // ProductRestController의 상품 등록 API URL로 변경
            dataType: 'json', // 서버로부터 받을 것으로 예상되는 데이터 타입
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data) // 데이터를 JSON 문자열로 변환하여 전송
        }).done(function(productIdResponse) { // 성공 시 ProductId를 응답으로 받음
            alert('상품이 등록되었습니다. 상품 ID: ' + productIdResponse);
            $('#formResult').text('상품이 성공적으로 등록되었습니다. 상품 ID: ' + productIdResponse).removeClass('error').addClass('success');
            // 성공 후 폼 초기화
            $('#productForm')[0].reset();
            // 필요하다면 다른 페이지로 리디렉션
            // window.location.href = '/';
        }).fail(function (jqXHR, textStatus, errorThrown) {
            var errorMessage = '상품 등록 중 오류가 발생했습니다.';
            if (jqXHR.responseJSON) { // Spring Boot에서 에러 발생 시 responseJSON에 상세 내용이 담겨오는 경우가 많음
                var responseError = jqXHR.responseJSON;
                if (responseError.message) {
                     errorMessage += '\n' + responseError.message;
                }
                // Validation 에러 메시지 처리 (Spring Boot 기본 에러 형식 참고)
                if (responseError.errors && Array.isArray(responseError.errors) && responseError.errors.length > 0) {
                    responseError.errors.forEach(function(err) {
                        errorMessage += '\n- ' + (err.defaultMessage || (err.field ? err.field + ': ' : '') + (err.code || ''));
                    });
                } else if (responseError.error) { // 일반적인 에러 설명
                    errorMessage += '\n' + responseError.error;
                }
            } else if (jqXHR.responseText) { // responseJSON이 없는 경우 raw responseText 사용 시도
                try {
                    var parsedResponse = JSON.parse(jqXHR.responseText);
                    if(parsedResponse && parsedResponse.message) {
                        errorMessage += '\n' + parsedResponse.message;
                    } else {
                       errorMessage += '\n' + jqXHR.responseText.substring(0, 200); // 너무 길 경우 일부만 표시
                    }
                } catch (e) {
                    errorMessage += '\n응답 텍스트: ' + jqXHR.responseText.substring(0, 200);
                }
            } else {
                errorMessage += '\n' + (errorThrown || textStatus);
            }
            alert(errorMessage);
            $('#formResult').text(errorMessage).removeClass('success').addClass('error');
        });
    }
};

// 페이지 로드 완료 시 init 함수 실행
$(document).ready(function() {
    productFormHandler.init();
});