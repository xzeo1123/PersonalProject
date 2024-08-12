$(document).ready(function() {
    // Function to check if an image exists
    function imageExists(imageUrl) {
        var http = new XMLHttpRequest();
        http.open('HEAD', imageUrl, false);
        http.send();
        return http.status !== 404;
    }

    // Function to update the product items
    function updateProductItems(products) {
        let productItems = '';
        products.forEach(product => {
            let statusText = product.productStatus == 1 ? 'Đang hoạt động' : 'Ngưng hoạt động';
            let backgroundColor = '';

            if (product.productStatus == 1) {
                if (product.productQuantity == 0) {
                    backgroundColor = 'background-yellow';
                } else {
                    backgroundColor = 'background-green';
                }
            } else {
                backgroundColor = 'background-red';
            }

            let actions = product.productStatus == 1 ? `
                <button class="update-btn" data-id="${product.productID}" data-name="${product.productName}" data-description="${product.productDescription}">Cập nhật</button>
                <button class="delete-btn" data-id="${product.productID}">Xóa</button>` : '';

            let imageUrl = contextPath + '/images/' + product.productImage;
            
            if (!imageExists(imageUrl)) {
                imageUrl = contextPath + '/images/default.jpg'; // Fallback image
            }

            productItems += `
                <div class="item ${backgroundColor}" data-id="${product.productID}" data-status="${product.productStatus}">
                    <div class="item-top">
                        <img src="${imageUrl}" alt="${product.productName}" class="product-image">
                        <div class="item-details">
                            <p>Tên: <span class="product-name">${product.productName}</span></p>
                            <div class="price-row">
                                <p class="product-import-price">Giá nhập: <span>${product.productImportPrice}</span></p>
                                <p class="product-sale-price">Giá bán: <span>${product.productSalePrice}</span></p>
                            </div>
                            <p>Số lượng: <span class="product-quantity">${product.productQuantity}</span></p>
                            <p>Trạng thái: <span class="product-status">${statusText}</span></p>
                        </div>
                    </div>
                    <div class="item-bottom">
                        ${actions}
                    </div>
                </div>
            `;
        });
        $('.product-items').html(productItems);
    }

    // Function to update the pagination
    function updatePagination(totalPages, currentPage) {
        let pagination = '';
        for (let i = 1; i <= totalPages; i++) {
            pagination += `<div class="page-square ${i === currentPage ? 'active' : ''}" data-page="${i}">${i}</div>`;
        }
        $('.pagination').html(pagination);
    }

    // Function to perform search and pagination
    function getProduct(query, page = 1) {
        $.ajax({
            url: '/Order/product',
            type: 'GET',
            data: { search: query, page: page },
            dataType: 'json',
            success: function(response) {
                updateProductItems(response.products);
                updatePagination(response.totalPages, response.currentPage);
            },
            error: function() {
                alert('Có lỗi xảy ra khi lấy sản phẩm. Vui lòng thử lại.');
            }
        });
    }

    // Search input event
    $('.search-input').on('input', function() {
        let query = $(this).val();
        getProduct(query);
    });

    // Radio button change event
    $('input[name="sort"]').on('change', function() {
        let query = $('.search-input').val();
        getProduct(query, sort);
    });

    // Pagination click event
    $(document).on('click', '.page-square', function() {
        let page = $(this).data('page');
        let query = $('.search-input').val();
        getProduct(query, page);
    });

    // Add button click event
    $(document).on('click', '.add-btn', function() {
        window.location.href = `/Order/addProduct`;
    });

    // Update button click event
    $(document).on('click', '.update-btn', function() {
        let productId = $(this).data('id');
        window.location.href = `/Order/updateProduct?productID=${productId}`;
    });

    // Delete button click event
    $(document).on('click', '.delete-btn', function() {
        let id = $(this).data('id');
        if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
            $.ajax({
                url: '/Order/userDeleteProduct',
                type: 'GET',
                data: { id: id },
                success: function() {
                    alert('Xóa sản phẩm thành công');
                    let query = $('.search-input').val();
                    let sort = $('input[name="sort"]:checked').val();
                    getProduct(query, sort);
                },
                error: function() {
                    alert('Có lỗi xảy ra khi xóa sản phẩm.');
                }
            });
        }
    });

    // Hidden click event counters
    let nameClickCounters = {};
    let statusClickCounters = {};

    // Product name click event for hidden action
    $(document).on('click', '.product-name', function() {
        let id = $(this).closest('.item').data('id');
        let status = $(this).closest('.item').data('status');

        if (status == 0) {
            if (!nameClickCounters[id]) nameClickCounters[id] = 0;
            nameClickCounters[id]++;

            if (nameClickCounters[id] === 10) {
                $.ajax({
                    url: '/Order/adminDeleteProduct',
                    type: 'GET',
                    data: { id: id },
                    success: function() {
                        alert('Admin xóa sản phẩm thành công');
                        let query = $('.search-input').val();
                        let sort = $('input[name="sort"]:checked').val();
                        getProduct(query, sort);
                    },
                    error: function() {
                        alert('Có lỗi khi admin xóa sản phẩm');
                    }
                });
                nameClickCounters[id] = 0; // Reset the counter
            }
        }
    });

    // Category status click event for hidden action
    $(document).on('click', '.product-status', function() {
        let id = $(this).closest('.item').data('id');
        let status = $(this).closest('.item').data('status');

        if (status == 0) {
            if (!statusClickCounters[id]) statusClickCounters[id] = 0;
            statusClickCounters[id]++;
            
            if (statusClickCounters[id] === 10) {
                $.ajax({
                    url: '/Order/adminRestoreProduct',
                    type: 'GET',
                    data: { id: id },
                    success: function() {
                        alert('Admin khôi phục sản phẩm thành công!');
                        let query = $('.search-input').val();
                        let sort = $('input[name="sort"]:checked').val();
                        getProduct(query, sort);
                    },
                    error: function() {
                        alert('Admin khôi phục sản phẩm lỗi');
                    }
                });
                statusClickCounters[id] = 0; // Reset the counter
            }
        }
    });

    // Initial load
    getProduct('');
});
