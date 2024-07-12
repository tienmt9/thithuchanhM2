package Business;

import Model.GenuinePhone;
import Model.ImportedPhone;
import Model.Phone;

import Exceptions.*;

import java.util.List;
import java.util.Scanner;

public class PhoneManager {
    private CsvFileHandler fileHandler;
    private List<String[]> phoneData;

    public PhoneManager(String filePath) {
        this.fileHandler = new CsvFileHandler(filePath);
        this.phoneData = fileHandler.readData();
    }

    public void addPhone() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Chọn loại điện thoại bạn muốn thêm:");
        System.out.println("1. Điện thoại chính hãng");
        System.out.println("2. Điện thoại xách tay");
        System.out.print("Chọn loại điện thoại (1/2): ");
        int type = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nhập thông tin điện thoại mới:");

        System.out.print("ID: ");
        String id = scanner.nextLine();

        System.out.print("Tên điện thoại: ");
        String name = scanner.nextLine();

        System.out.print("Giá bán: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Số lượng: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhà sản xuất: ");
        String manufacturer = scanner.nextLine();

        if (type == 1) {
            System.out.print("Thời gian bảo hành (tháng): ");
            int warrantyPeriod = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Phạm vi bảo hành (trong nước hoặc quốc tế): ");
            String warrantyScope = scanner.nextLine();

            GenuinePhone genuinePhone = new GenuinePhone(id, name, price, quantity, manufacturer,
                    warrantyPeriod, warrantyScope);
            addPhone(genuinePhone);
        } else if (type == 2) {
            System.out.print("Quốc gia xách tay: ");
            String importCountry = scanner.nextLine();

            System.out.print("Trạng thái: ");
            String status = scanner.nextLine();

            ImportedPhone importedPhone = new ImportedPhone(id, name, price, quantity, manufacturer, importCountry, status);
            addPhone(importedPhone);
        } else {
            Phone phone = new Phone(id, name, price, quantity, manufacturer);
            addPhone(phone);
        }
    }

    public void addPhone(Phone phone) {
        String[] newData = phoneToCsvFormat(phone);
        phoneData.add(newData);
        fileHandler.writeData(phoneData);
        System.out.println("Điện thoại đã được thêm mới vào hệ thống.");
    }

    public void deletePhone(String id) {
        int index = findPhoneIndexById(id);
        if (index != -1) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Bạn có chắc chắn muốn xóa điện thoại có ID " + id + "? (Yes/No)");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("yes")) {
                phoneData.remove(index);
                fileHandler.writeData(phoneData);
                System.out.println("Điện thoại đã được xóa khỏi hệ thống.");
            } else if (confirmation.equals("no")) {
                System.out.println("Hủy bỏ xóa điện thoại.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Hủy bỏ xóa điện thoại.");
            }
        } else {
            try {
                throw new NotFoundProductException("ID điện thoại không tồn tại.");
            } catch (NotFoundProductException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private int findPhoneIndexById(String id) {
        for (int i = 0; i < phoneData.size(); i++) {
            String[] phone = phoneData.get(i);
            if (phone[0].equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private String[] phoneToCsvFormat(Phone phone) {
        if (phone instanceof GenuinePhone) {
            GenuinePhone genuinePhone = (GenuinePhone) phone;
            return new String[]{
                    phone.getId(),
                    phone.getName(),
                    String.valueOf(phone.getPrice()),
                    String.valueOf(phone.getQuantity()),
                    phone.getManufacturer(),
                    String.valueOf(genuinePhone.getWarrantyPeriod()),
                    genuinePhone.getWarrantyScope(),
                    "", ""
            };
        } else if (phone instanceof ImportedPhone) {
            ImportedPhone importedPhone = (ImportedPhone) phone;
            return new String[]{
                    phone.getId(),
                    phone.getName(),
                    String.valueOf(phone.getPrice()),
                    String.valueOf(phone.getQuantity()),
                    phone.getManufacturer(),
                    "", "",
                    importedPhone.getImportCountry(),
                    importedPhone.getStatus()
            };
        } else {
            return new String[]{
                    phone.getId(),
                    phone.getName(),
                    String.valueOf(phone.getPrice()),
                    String.valueOf(phone.getQuantity()),
                    phone.getManufacturer(),
                    "", "",
                    "", ""
            };
        }
    }

    public void viewPhoneList() {
        System.out.println("Danh sách điện thoại:");
        System.out.printf("%-10s %-20s %-10s %-10s %-20s %-20s %-20s\n", "ID", "Tên", "Giá bán", "Số lượng", "Nhà sản xuất", "Bảo hành", "Quốc gia xách tay");

        for (String[] phone : phoneData) {
            String id = phone[0];
            String name = phone[1];
            String price = phone[2];
            String quantity = phone[3];
            String manufacturer = phone[4];
            String warranty = phone[5];
            String importCountry = phone[7]; // Dành cho điện thoại xách tay

            // In thông tin của từng điện thoại dựa vào loại
            if (!warranty.isEmpty()) { // Điện thoại chính hãng
                System.out.printf("%-10s %-20s %-10s %-10s %-20s %-20s %-20s\n", id, name, price, quantity, manufacturer, warranty, "");
            } else if (!importCountry.isEmpty()) { // Điện thoại xách tay
                String status = phone[8];
                System.out.printf("%-10s %-20s %-10s %-10s %-20s %-20s %-20s\n", id, name, price, quantity, manufacturer, "", importCountry);
            } else { // Điện thoại thông thường
                System.out.printf("%-10s %-20s %-10s %-10s %-20s %-20s %-20s\n", id, name, price, quantity, manufacturer, "", "");
            }
        }
    }

}

