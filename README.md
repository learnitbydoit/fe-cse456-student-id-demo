# Note 14/8/2025
## Viết Controller cho link remove / xóa
1. Viết JavaScript thông báo **Yes/No** (trên file `trang-danh-sach.html`).
2. Gọi service xóa.

---

## Viết Controller tìm kiếm
1. **Chuẩn bị**:
    1.1. Bổ sung service gọi repo `searchAllByName...` (trả về danh sách).  
    1.2. Bổ sung thêm repo `searchAllByName...` (nếu hàm chưa có Built-in thì tự generate theo *Derived Query Language*).

2. **`trang-danh-sach.html`**:  
   - Bổ sung ô search (form dùng **GET** → *giải thích tại sao dùng GET*).

3. **Controller**:
    3.1. Bổ sung `@RequestParam` để lấy `keyword` (thêm thuộc tính `required = false` → *giải thích lý do*).  
    3.2. Sử dụng `if()` kiểm tra `keyword` và trả về danh sách tương ứng.

---

## Xử lý ngoại lệ / bắt lỗi
1. Tạo entity `ErrorResponse` để format lỗi ngắn gọn.
2. Tạo package `exception` → class `GlobalExceptionHandler`.

---

## Sử dụng log để kiểm tra
- Dùng log trong danh sách controller để theo dõi luồng xử lý.

---

## Phân biệt khi nào Edit / khi nào Add New
1. Dùng `modelAttribute` chung (flag) **formMode**:
    - 1.1. Hàm xử lý edit: gửi kèm `formMode = edit`.  
    - 1.2. Hàm xử lý new: gửi kèm `formMode = new`.

2. Trên file HTML:
    - 2.1. Dùng `if` để xử lý tiêu đề là **edit/new**.  
    - 2.2. `th:readonly` = kiểm tra nếu `formMode = edit`.

---

## Validate dữ liệu
> Xem ghi chú trước `@PostMapping` của **Save** dữ liệu nhập.

### Các bước thực hiện:
1. Bổ sung dependency **Validation** (Bean Validation).
2. Khai báo các annotation bắt lỗi trong entity.
3. Kích hoạt trong controller:
    - 3.1. Phân biệt **edit/new** (bằng hidden input trong form):
        - 3.1.1. Nếu có lỗi → trả về trang form kèm `category` + `formMode`.
    - 3.2. Thêm `@Valid` trước khi binding data từ view xuống.
    - 3.3. Thêm biến `BindingResult` để trả về các message thông báo cho Thymeleaf hiển thị (*thường dùng trong thẻ `<span>`*).
