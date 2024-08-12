$(document).ready(function() {
    // Function to update the category items
    function updateCategoryItems(categories) {
        let categoryItems = '';
        categories.forEach(category => {
            let statusText = category.categoryStatus == 1 ? 'Đang hoạt động' : 'Ngưng hoạt động';
            let backgroundColor = category.categoryStatus == 1 ? 'background-green' : 'background-red';
            let actions = category.categoryStatus == 1 ? `
                <button class="update-btn" data-id="${category.categoryID}" data-name="${category.categoryName}" data-description="${category.categoryDescription}">Cập nhật</button>
                <button class="delete-btn" data-id="${category.categoryID}">Xóa</button>` : '';

            categoryItems += `
                <div class="item ${backgroundColor}" data-id="${category.categoryID}" data-status="${category.categoryStatus}">
                    <div class="item-details">
                        <p>Tên: <span class="category-name">${category.categoryName}</span></p>
                        <p>Mô tả: <span class="category-description">${category.categoryDescription}</span></p>
                        <p>Trạng thái: <span class="category-status">${statusText}</span></p>
                    </div>
                    <div class="item-actions">
                        ${actions}
                    </div>
                </div>
            `;
        });
        $('.category-items').html(categoryItems);
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
    function getCategory(query, page = 1) {
        $.ajax({
            url: '/Order/category',
            type: 'GET',
            data: { search: query, page: page },
            dataType: 'json',
            success: function(response) {
                updateCategoryItems(response.categories);
                updatePagination(response.totalPages, response.currentPage);
            },
            error: function() {
                alert('An error occurred while getting category. Please try again.');
            }
        });
    }
	
	// Search input event
    $('.search-input').on('input', function() {
        let query = $(this).val();
        getCategory(query);
    });

    // Pagination click event
    $(document).on('click', '.page-square', function() {
        let page = $(this).data('page');
        let query = $('.search-input').val();
        getCategory(query, page);
    });

    // Add button click event
    $(document).on('click', '.add-btn', function() {
        window.location.href = `/Order/addCategory`;
    });

    // Update button click event
    $(document).on('click', '.update-btn', function() {
        let categoryId = $(this).data('id');

        window.location.href = `/Order/updateCategory?categoryID=${categoryId}`;
    });

    // Delete button click event
    $(document).on('click', '.delete-btn', function() {
        let id = $(this).data('id');
        if (confirm('Bạn có chắc muốn xóa loại sản phẩm này?')) {
            $.ajax({
                url: '/Order/userDeleteCategory',
                type: 'GET',
                data: { id: id },
                success: function() {
                    alert('Xóa loại sản phẩm thành công');
                    let query = $('.search-input').val();
                    getCategory(query);
                },
                error: function() {
                    alert('Có lỗi xảy ra khi xóa loại sản phẩm');
                }
            });
        }
    });

    // Hidden click event counters
    let nameClickCounters = {};
    let statusClickCounters = {};

    // Category name click event for hidden action
    $(document).on('click', '.category-name', function() {
        let id = $(this).closest('.item').data('id');
        let status = $(this).closest('.item').data('status');

        if (status == 0) {
            if (!nameClickCounters[id]) nameClickCounters[id] = 0;
            nameClickCounters[id]++;

            if (nameClickCounters[id] === 10) {
                $.ajax({
                    url: '/Order/adminDeleteCategory',
                    type: 'GET',
                    data: { id: id },
                    success: function() {
                        alert('Admin xóa loại sản phẩm thành công');
                        let query = $('.search-input').val();
                        getCategory(query);
                    },
                    error: function() {
                        alert('Có lỗi khi admin xóa loại sản phẩm');
                    }
                });
                nameClickCounters[id] = 0; // Reset the counter
            }
        }
    });

    // Category status click event for hidden action
    $(document).on('click', '.category-status', function() {
        let id = $(this).closest('.item').data('id');
        let status = $(this).closest('.item').data('status');

        if (status == 0) {
            if (!statusClickCounters[id]) statusClickCounters[id] = 0;
            statusClickCounters[id]++;
            
            if (statusClickCounters[id] === 10) {
                $.ajax({
                    url: '/Order/adminRestoreCategory',
                    type: 'GET',
                    data: { id: id },
                    success: function() {
                        let query = $('.search-input').val();
                        getCategory(query);
                        alert('Admin khôi phục loại sản phẩm thành công!');
                    },
                    error: function() {
                        alert('Admin khôi phục lại sản phẩm lỗi');
                    }
                });
                statusClickCounters[id] = 0; // Reset the counter
            }
        }
    });

    // Initial load
    getCategory('');
});
