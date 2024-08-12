$(document).ready(function () {
    function loadProducts() {
        const search = $('.search-input').val();
        const categoryID = $('.category-filter').val();
        const sortOrder = $('input[name="sortOrder"]:checked').val();

        $.ajax({
            url: `${contextPath}/invoice`,
            method: 'POST',
            data: {
                action: 'filterProducts',
                search: search,
                categoryID: categoryID,
                sortOrder: sortOrder
            },
            success: function (data) {
                let productTableBody = $('#product-table-body');
                productTableBody.empty();

                data.forEach(function (product) {
                    productTableBody.append(`
                        <tr class="product-row" data-product-id="${product.productID}" data-product-name="${product.productName}" data-product-price="${product.productImportPrice}">
                            <td><img src="${contextPath}/images/${product.productImage}" alt="Product Image" class="product-image"></td>
                            <td>${product.productName}</td>
                            <td>${product.productImportPrice.toLocaleString('vi-VN')}</td>
                            <td>${product.productQuantity}</td>
                        </tr>
                    `);
                });
            }
        });
    }

    function updateTotalInvoiceAmount() {
        let total = 0;
        $('#invoice-table-body tr').each(function () {
            const totalPriceText = $(this).find('.total-price').text().replace(/\./g, '');
            const totalPrice = parseFloat(totalPriceText);
            if (!isNaN(totalPrice)) {
                total += totalPrice;
            }
        });
        $('#totalAmount').text(total.toLocaleString('vi-VN') + ' VND');
        $('#totalAmountHidden').val(total);
    }

    $(document).on('mouseenter', '.product-row', function () {
        $(this).css('background-color', '#f0f0f0');
    });

    $(document).on('mouseleave', '.product-row', function () {
        $(this).css('background-color', '');
    });

    $(document).on('click', '.product-row', function () {
        const productId = $(this).data('product-id');
        const productName = $(this).data('product-name');
        const productPrice = parseFloat($(this).data('product-price').toString().replace(/\./g, ''));

        $(this).addClass('selected').siblings().removeClass('selected');

        let existingRow = $('#invoice-table-body').find(`tr[data-product-id='${productId}']`);

        if (existingRow.length) {
            let quantityInput = existingRow.find('.quantity-input');
            quantityInput.val(parseInt(quantityInput.val(), 10) + 1);
            quantityInput.trigger('input');
        } else {
            if (!isNaN(productPrice)) {
                const invoiceRow = `
                    <tr data-product-id="${productId}" data-product-price="${productPrice}">
                        <td>${productName}</td>
                        <td><input type="number" class="quantity-input" value="1" min="1"></td>
                        <td class="total-price">${productPrice.toLocaleString('vi-VN')}</td>
                        <td><button type="button" class="delete-btn"><i class="fas fa-trash"></i></button></td>
                    </tr>
                `;
                $('#invoice-table-body').append(invoiceRow);
                updateTotalInvoiceAmount();
            }
        }
    });

    $(document).on('input', '.quantity-input', function () {
        const quantity = parseFloat($(this).val());
        const productRow = $(this).closest('tr');
        const productPrice = parseFloat(productRow.data('product-price').toString().replace(/\./g, ''));

        if (!isNaN(quantity) && !isNaN(productPrice)) {
            const totalPrice = quantity * productPrice;
            productRow.find('.total-price').text(totalPrice.toLocaleString('vi-VN'));
            updateTotalInvoiceAmount();
        }
    });

    $(document).on('click', '.delete-btn', function () {
        $(this).closest('tr').remove();
        updateTotalInvoiceAmount();
    });

    $('.search-input').on('input', loadProducts);
    $('.category-filter').on('change', loadProducts);
    $('input[name="sortOrder"]').on('change', loadProducts);

    loadProducts();
    
    $('#confirm-invoice-btn').on('click', function () {
        const invoiceItems = $('#invoice-table-body tr');
        if (invoiceItems.length === 0) {
            alert("Chưa có mặt hàng nào được chọn!");
            return;
        }
    
        if (confirm("Bạn có chắc chắn muốn nhập hàng không?")) {
            invoiceItems.each(function (index) {
                const productId = $(this).data('product-id');
                const quantity = parseInt($(this).find('.quantity-input').val(), 10);
    
                $('<input>').attr({
                    type: 'hidden',
                    name: 'productID' + index,
                    value: productId
                }).appendTo('#invoice-form');
    
                $('<input>').attr({
                    type: 'hidden',
                    name: 'quantity' + index,
                    value: quantity
                }).appendTo('#invoice-form');
            });
    
            $('#invoice-form').submit();
        }
    });
});
