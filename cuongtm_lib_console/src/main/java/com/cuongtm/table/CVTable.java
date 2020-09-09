package com.cuongtm.table;

import com.cuongtm.utils.BaseCheckUltils;
import com.cuongtm.utils.Constant;
import com.cuongtm.utils.PrintWithColor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author CuongTM
 */

public class CVTable {

    private static StringBuilder datatableString = null;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static List<String> listMenu;

    /**
     * @param header : tiêu đề Menu
     * @apiNote Hàm in Menu
     */
    public static void menu(String header, String object) {
        listMenu = new ArrayList<>(Arrays.asList(
                String.format(Constant.FUNC_SEARCH, object),
                String.format(Constant.FUNC_EDIT, object),
                String.format(Constant.FUNC_SEND, object),
                String.format(Constant.FUNC_WITHDRAW, object),
                String.format(Constant.FUNC_EXIT, object)));
        tableMenu(header, listMenu);
    }


    /**
     * @param header   : tiêu đề Menu
     * @param menuName : List tên Menu
     * @apiNote Hàm tạo bảng Menu
     */
    private static void tableMenu(String header, List<String> menuName) {
        int menuRollNo = 1;
        datatableString = new StringBuilder();
        //Tiêu đề menu
        //datatableString.append(Constant.MENU_TABLE_BOUND);
        datatableString.append(StringUtils.center(header, Constant.SIZE_MENU_FUNCTION) + Constant.NEW_LINE);
        datatableString.append(Constant.MENU_FUNCTION_BOUND);

        //Duyệt và in ra tên chức năng
        for (String menu : menuName) {
            datatableString.append(String.format(Constant.ALIGN_FORMAT, StringUtils.center(String.valueOf(menuRollNo++), Constant.SIZE_MENU_PIN), menu));
            datatableString.append(Constant.MENU_FUNCTION_BOUND);
        }

        //Chọn chức năng
        datatableString.append("======> Chọn chức năng ( 1 -> " + (menuName.size()) + " ): ");
        PrintWithColor.printDataTable(datatableString.toString());
    }


    /**
     * @param collectionInput : Danh sách dữ liệu
     * @param clazz           : Class type của danh sách dữ liệu
     * @apiNote Hàm in bảng dữ liệu
     */
    public static <T> void dataTable(Collection<T> collectionInput, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        datatableString = new StringBuilder();
        T object = getClassNewInstanceEx(clazz);
        String bound = Constant.DATATABLE_PIN_BOUND;

        //Lấy ra danh sách các Attributes và danh sách AttributeName
        List<Field> listField = new ArrayList<>(Arrays.asList(object.getClass().getDeclaredFields()));
        List<String> actualFieldNames = getFieldNames(listField);
        datatableString.append(Constant.NEW_LINE);

        //Tạo đường biên cho bảng
        for (int i = 1; i < listField.size(); i++) {
            bound += Constant.DATATABLE_FIELD_BOUND;
        }
        datatableString.append(bound + Constant.END_TABLE_LINE);

        //Tạo tiêu đề bảng
        datatableString.append("|" + StringUtils.center(String.format(Constant.DATATABLE_HEADER, StringUtils.upperCase(object.getClass().getSimpleName()))
                , bound.length() - 1) + Constant.END_ROW_DATA);
        datatableString.append(bound + Constant.END_TABLE_LINE);

        //Tạo dòng tiêu đề cho các Attributes
        int count = Constant.FIRST_ELEMENT;
        for (String fieldName : actualFieldNames) {
            if (count++ == Constant.FIRST_ELEMENT) {
                appendStrCenter(StringUtils.upperCase(fieldName), Constant.SIZE_DATATABLE_PIN);
            } else {
                appendStrCenter(StringUtils.upperCase(fieldName), Constant.SIZE_DATATABLE_ROW);
            }
        }
        datatableString.append(Constant.END_ROW_DATA + bound + Constant.END_TABLE_LINE);

        int rowCount = Constant.FIRST_ELEMENT;
        //Kiểm tra đầu vào không null và có phần tử
        if (BaseCheckUltils.checkCollectionNotNull(collectionInput)) {
            //In dữ liệu các Attributes của từng phần tử trong collectionInput ra bảng
            count = Constant.FIRST_ELEMENT;
            for (T obj : collectionInput) {
                for (Field field : listField) {
                    field.setAccessible(true);
                    if (count++ == Constant.FIRST_ELEMENT) {
                        appendStrCenter(field.get(obj), Constant.SIZE_DATATABLE_PIN);
                    } else {
                        appendStrDatatable(field.get(obj), Constant.SIZE_DATATABLE_ROW);
                    }
                }
                //Phân biệt hết dữ liệu mỗi dòng
                count = Constant.FIRST_ELEMENT;
                datatableString.append(Constant.END_ROW_DATA);
                datatableString.append(bound + Constant.END_TABLE_LINE);
                //Đếm số phần tử trong Danh sách
                rowCount++;
            }
        } else {
            //Nếu collectionInput rỗng thì in ra bảng trống
            datatableString.append("|" + StringUtils.rightPad(Constant.NULL_DATATABLE_INFO, bound.length() - 1) + Constant.END_ROW_DATA);
            datatableString.append(bound + Constant.END_TABLE_LINE);
        }

        //Tạo dòng tổng số bản ghi
        datatableString.append("|" + StringUtils.rightPad(String.format(Constant.ROW_COUNT, rowCount), bound.length() - 1) + Constant.END_ROW_DATA);
        datatableString.append(bound + Constant.END_TABLE_LINE);
        datatableString.append(Constant.NEW_LINE);
        //In ra bảng dữ liệu
        PrintWithColor.printDataTable(datatableString.toString());
    }

    /**
     * @param fields : Danh sách Attributes
     * @apiNote Hàm lấy danh sách tên Attributes
     */
    private static List<String> getFieldNames(List<Field> fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields) {
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    /**
     * @param clazz : Danh sách Attributes
     * @apiNote Hàm lấy new Instance của class con kế thừa
     */
    private static <T> T getClassNewInstanceEx(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    /**
     * @param obj : Đối tượng cần check
     * @apiNote Hàm boolean check đối tượng null hay không
     */
    private static boolean checkNullField(Object obj) {
        return obj != null;
    }

    /**
     * @param obj : dữ liệu của attribute
     * @apiNote Hàm lấy giá trị String của đối tượng
     */
    private static String objFieldData(Object obj) {
        if (checkNullField(obj)) {
            if (checkObjectTypeOfDate(obj)) {
                return dateFormat.format(obj);
            }
            return obj.toString();
        } else {
            return Constant.SPACE;
        }
    }

    /**
     * @param obj : dữ liệu của attribute
     * @apiNote Hàm boolean kiểm tra đối tượng có thuộc type java.util.Date
     */
    private static boolean checkObjectTypeOfDate(Object obj) {
        return obj instanceof Date;
    }

    private static void appendStrCenter(Object obj, int size) {
        datatableString.append("|" + StringUtils.center(objFieldData(obj), size));
    }

    private static void appendStrRight(Object obj, int size) {
        datatableString.append("|" + StringUtils.rightPad(Constant.SPACE + objFieldData(obj), size));
    }

    private static void appendStrLeft(Object obj, int size) {
        datatableString.append("|" + StringUtils.leftPad(objFieldData(obj), size) + Constant.SPACE);
    }

    private static void appendStrDatatable(Object obj, int size) {
        if (checkObjectTypeOfDate(obj)) {
            appendStrCenter(obj, size);
        } else {
            appendStrRight(obj, size);
        }
    }

    /**
     * @param menuName : tên chức năng muốn thêm
     * @apiNote Hàm thêm chức năng vào menu
     */
    public static void addMenuToListMenu(String... menuName) {
        for (String menu : menuName) {
            listMenu.add(4, menu);
        }
    }

}
