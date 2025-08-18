# Note 18/8/2025
## Bảo mật app thì có 2 việc: Authentication & Authorization

### 1. Authentication (Xác thực bằng login)
Khi xử lý login, có những trường hợp xảy ra:

- **Username sai** → báo lỗi *account sai, không tồn tại* → gợi ý *sign up*.  
- **Username đúng, password sai** → báo lỗi *password*.  
  - Nếu nhập sai nhiều lần (>3) → Reset password.  
  - Nếu tiếp tục sai (>5) → Khóa tài khoản.  
- **Username đúng, password đúng, role = member** → chỉ có quyền *readonly*.  
- **Username đúng, password đúng, role = admin/staff & active = false** → *invalid credentials*.  
- **Username đúng, password đúng, role = admin/staff & active = true** → vào trang `/products` với quyền cụ thể.

---

### 2. Authorization (Phân quyền truy cập)
- **Admin**: full CRUD.  
- **Staff**: xem toàn bộ dữ liệu, tìm kiếm, chỉ CRUD trên một số entity nhất định.  
- **Member**: chỉ xem.  

---

### 3. Gõ trực tiếp URL
- Nếu chưa login mà gõ trực tiếp → chuyển sang trang login.  
- Phải khởi động từ màn hình chính `/products` (??).  

---

### Lưu ý
- **Model**: chuyển dữ liệu qua lại giữa các trang nhưng *không lưu giữ lâu dài*.  
- **HttpSession**: giữ dữ liệu lâu dài qua nhiều trang (tồn tại mặc định 30 phút).
---
## Xử lý cụ thể

### Báo popup và chặn, quay lại login khi nhập link trực tiếp, viết ở đầu trang login
<!-- Script xử lý thông báo nếu được redirect sang.
     Do có lấy dữ liệu từ server nên cần `th:inline`
     để Thymeleaf render đúng chuẩn dữ liệu:
     - string có nháy
     - số thì không
     - object >> JSON
-->
```html
<script th:inline="javascript">
    var isLogin = /*[[${noLogin}]]*/ null;
    if (isLogin == null) {
        alert("Access denied. You must login first.");
        // document.getElementById("msg").textContent = "Access denied. You must login first!";
    }
</script>


---
# Note 14/8/2025
## Viết Controller cho link add new
---
## Phân biệt khi nào Edit / khi nào Add New
1. Dùng `modelAttribute` chung (flag) **formMode**:
    - 1.1. Hàm xử lý edit: gửi kèm `formMode = edit`.
    - 1.2. Hàm xử lý new: gửi kèm `formMode = new`.

2. Trên file HTML:
    - 2.1. Dùng `if` để xử lý tiêu đề là **edit/new**.
    - 2.2. `th:readonly` = kiểm tra nếu `formMode = edit`.

---
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

## Validate dữ liệu
> Xem ghi chú trước `@PostMapping` của **Save** dữ liệu nhập.
### Hàm `save` để xử lý link **save**

- **Chặn đầu chặn đuôi** trong quá trình binding dữ liệu từ form lên object.
- **Kỹ thuật Bean Validation** chỉ sử dụng nếu binding bằng **Object**.
- **Chặn đầu** qua `@Valid` để kích hoạt việc kiểm soát từng field (đã được khai báo trong entity).
- Nếu phát hiện **có lỗi** thì ghi biên bản vi phạm qua `BindingResult` (message được khai báo trong entity).
- `BindingResult` sẽ được gửi kèm với `Model` và trả trở lại form nếu muốn người dùng **ở lại form để sửa**.


### Các bước thực hiện:
1. Bổ sung dependency **Validation** (Bean Validation).
2. Khai báo các annotation bắt lỗi trong entity.
3. Kích hoạt trong controller:
    - 3.1. Phân biệt **edit/new** (bằng hidden input trong form):
        - 3.1.1. Nếu có lỗi → trả về trang form kèm `majorList` + `formMode`.
    - 3.2. Thêm `@Valid` trước khi binding data từ view xuống.
    - 3.3. Thêm biến `BindingResult` để trả về các message thông báo cho Thymeleaf hiển thị (*thường dùng trong thẻ `<span>`*).
    - 3.4. Dùng th:errors=*{tên thuộc tính binding} để hiển thị thông báo lỗi.
