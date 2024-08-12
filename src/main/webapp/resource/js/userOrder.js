$(document).ready(function() {
    let selectedCategoryId = 'all';

	function loadProducts() {
	    const search = $('.search-input').val();

	    $.ajax({
	        url: `${contextPath}/userorder`,
	        method: 'POST',
	        data: {
	            search: search,
	            categoryID: selectedCategoryId
	        },
	        success: function (data) {
	            let itemsList = $('#items-list');
	            itemsList.empty();

	            data.forEach(function (product) {
	                let actionButton;

	                if (product.productQuantity > 0) {
	                    actionButton = `
	                        <button class="add-to-order" data-product-id="${product.productID}" data-product-name="${product.productName}" data-product-price="${product.productSalePrice}">
	                            Thêm vào giỏ hàng
	                        </button>`;
	                } else {
	                    actionButton = `<p class="out-of-stock">Hết hàng</p>`;
	                }

	                itemsList.append(`
	                    <div class="item-box">
	                        <div class="item-info">
	                            <div class="left">
	                                <img src="${contextPath}/images/${product.productImage}" alt="${product.productName}">
	                            </div>
	                            <div class="right">
	                                <div class="details">
	                                    <p class="name">${product.productName}</p>
	                                    <p class="price">${product.productSalePrice} VND</p>
										<p class="quantity">Còn ${product.productQuantity}sản phẩm</p>
	                                </div>
	                                <div class="item-action">
	                                    ${actionButton}
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                `);
	            });
	        }
	    });
	}

    // Handle category selection
    $('.category').on('click', function() {
        $('.category').removeClass('active');
        $(this).addClass('active');
        selectedCategoryId = $(this).data('id');
        loadProducts();
    });

    // Handle search input
    $('.search-input').on('input', loadProducts);

    // Handle tab switching
    const orderTab = document.getElementById('order-tab');
    const billTab = document.getElementById('bill-tab');
    const orderSection = document.querySelector('.order-section');
    const orderSummary = document.querySelector('.order-summary');

    orderTab.addEventListener('click', () => {
        orderTab.classList.add('active');
        billTab.classList.remove('active');
        orderSection.style.display = 'block';
        orderSummary.style.display = 'none';
    });

    billTab.addEventListener('click', () => {
        orderTab.classList.remove('active');
        billTab.classList.add('active');
        orderSection.style.display = 'none';
        orderSummary.style.display = 'block';
    });

    // Handle delete order
    $(document).on('click', '.delete-order', function() {
        $(this).closest('tr').remove();
        updateOrderTotal();
    });

    // Handle add to order
    $(document).on('click', '.add-to-order', function() {
        const productID = $(this).data('product-id');
        const productName = $(this).data('product-name');
        const productPrice = $(this).data('product-price');
        let orderTableBody = $('.order-table tbody');

        let existingRow = orderTableBody.find(`tr[data-product-id="${productID}"]`);

        if (existingRow.length > 0) {
            let quantityInput = existingRow.find('.quantity-input');
            quantityInput.val(parseInt(quantityInput.val()) + 1);
            updateProductTotal(existingRow);
        } else {
            let newRow = `
                <tr data-product-id="${productID}">
                    <td class="name-column">${productName}</td>
                    <td class="price-column">${productPrice} VND</td>
                    <td class="quantity-column">
                        <input type="number" class="quantity-input" value="1" min="1" style="width: 50px;">
                    </td>
                    <td class="total-column">${productPrice} VND</td>
                    <td class="action-column"><button class="delete-order"><i class="fa fa-trash"></i></button></td>
                </tr>
            `;
            orderTableBody.append(newRow);
        }

        updateOrderTotal();

        // Add blink class to bill tab
        $('#bill-tab').addClass('blink');
        setTimeout(function() {
            $('#bill-tab').removeClass('blink');
        }, 1000);
    });

    // Handle quantity change
    $(document).on('input', '.quantity-input', function() {
        let row = $(this).closest('tr');
        updateProductTotal(row);
        updateOrderTotal();
    });

    function updateProductTotal(row) {
        let quantity = parseInt(row.find('.quantity-input').val());
        let price = parseInt(row.find('.price-column').text().replace(' VND', '').replace(',', ''));
        let total = quantity * price;
        row.find('.total-column').text(total + ' VND');
    }

    function updateOrderTotal() {
        let total = 0;
        $('.order-table tbody tr').each(function() {
            let rowTotal = parseInt($(this).find('.total-column').text().replace(' VND', '').replace(',', ''));
            total += rowTotal;
        });
        $('#orderTotal').text(total + ' VND');
    }

	// Function to get URL parameters
	    function getUrlParameter(name) {
	        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
	        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
	        var results = regex.exec(location.search);
	        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
	    }

	    // Get tableID from the URL
	    let tableID = getUrlParameter('tableID');

	    // Handle form submission
	    $("#continue-order").click(function(event) {
	        event.preventDefault();

	        var orderItems = [];
	        var orderTotal = 0;
	        var orderTableBody = $(".order-table tbody tr");

	        if (orderTableBody.length === 0) {
	            alert("Bạn chưa chọn món nào. Vui lòng chọn món trước khi tiếp tục.");
	            return;
	        }

	        orderTableBody.each(function() {
	            var item = {
	                billID: 0,
	                productID: $(this).data("product-id"),
	                billDetailQuantity: parseInt($(this).find(".quantity-input").val(), 10)
	            };
	            orderItems.push(item);
	        });

	        orderTotal = parseInt($('#orderTotal').text().replace(' VND', '').replace(',', ''), 10);

	        $("<input>").attr({
	            type: "hidden",
	            name: "orderItems",
	            value: JSON.stringify(orderItems)
	        }).appendTo("#orderForm");

	        $("<input>").attr({
	            type: "hidden",
	            name: "orderTotal",
	            value: orderTotal
	        }).appendTo("#orderForm");

	        $("<input>").attr({
	            type: "hidden",
	            name: "tableID",
	            value: tableID
	        }).appendTo("#orderForm");

	        $(".order-table tbody").empty();
	        updateOrderTotal();

	        $("#orderForm").submit();
	    });

    // Initial load
    loadProducts();
});
