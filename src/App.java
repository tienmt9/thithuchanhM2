import Business.PhoneManager;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PhoneManager manager = new PhoneManager("src/phones.csv");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("----- MENU -----");
            System.out.println("1. Thêm mới điện thoại");
            System.out.println("2. Xóa điện thoại");
            System.out.println("3. Xem danh sách điện thoại");
            System.out.println("4. Tìm kiếm điện thoại");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manager.addPhone();
                    break;
                case 2:
                    System.out.print("Nhập ID điện thoại cần xóa: ");
                    String deleteId = scanner.nextLine();
                    manager.deletePhone(deleteId);
                    break;
                case 3:
                    manager.viewPhoneList();
                    break;
                case 4:
                    break;
                case 5:
                    System.out.println("Đã thoát chương trình.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        }
    }
}
