package com.example.service;

import JavaBean.good;
import JavaBean.good_order;
import JavaBean.order;
import com.example.Constans;
import com.example.mapper.GoodsMapper;
import com.example.mapper.OGMapper;
import com.example.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
public class Tools {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OGMapper ogMapper;



    /**
     * 程序主入口方法
     */
    @Transactional
    public  ArrayList<String> startSystem() {
        while (true) {
            System.out.println("\n=== 管理系统 ===");
            System.out.println("1. 商品管理");
            System.out.println("2. 订单管理");
            System.out.println("0. 退出");
            System.out.print("请选择: ");

            int choice = Constans.SC.nextInt();
            Constans.SC.nextLine();

            switch (choice) {
                case 1:
                    enterManagementGoodsSystem();
                    break;
                case 2:
                    enterManagementOrderSystem();
                    break;
                case 0:
                    System.out.println("退出系统");
                    Constans.SC.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效选择");
            }
        }
    }
    @Transactional
    public ArrayList<String> enterManagementGoodsSystem() {
        System.out.println("\n=== 商品管理系统 ===");
        System.out.println("1. 添加商品  2. 查询商品  3. 删除商品  4. 修改商品 5.查询商品(分页) 0. 返回");
        System.out.print("请选择: ");
        int choice = Constans.SC.nextInt();
        Constans.SC.nextLine();

        switch (choice) {
            case 1: Constans.answer.add(AddGoods());break;
            case 2: GetGoods(); break;
            case 3: Constans.answer.add(DeleteGoods());break;
            case 4: Constans.answer.add(UpdateGoods());break;
            case 5: Constans.answer.add(findByPage());break;
            case 0:
                System.out.println("返回主菜单");
                return Constans.answer;
            default: System.out.println("无效选择");
        }
        return Constans.answer;
    }
    @Transactional
    public ArrayList<String> enterManagementOrderSystem() {
        System.out.println("\n=== 订单管理系统 ===");
        System.out.println("1. 添加订单  2. 查询订单  3. 删除订单  4. 修改订单  0. 返回");
        System.out.print("请选择: ");
        int choice = Constans.SC.nextInt();
        Constans.SC.nextLine();

        switch (choice) {
            case 1: Constans.answer.add(AddOrder());break;
            case 2: GetAllOrders(); break;
            case 3: Constans.answer.add(DeleteOrder()); break;
            case 4: Constans.answer.add(UpdateOrder()); break;
            case 0:
                System.out.println("返回主菜单");
                return  Constans.answer;
            default: System.out.println("无效选择");
        }
        return Constans.answer;
    }

    public String AddGoods() {
        System.out.println("\n=== 添加商品 ===");
        System.out.print("商品名称: ");
        String name = Constans.SC.nextLine();

        System.out.print("商品价格: ");
        double price = Constans.SC.nextDouble();

        good goods = new good(null, name, price);
        int result = goodsMapper.insertGoods(goods);

        if (result > 0) {
            System.out.println("✅ 添加成功");
            return "商品添加成功";
        } else {
            System.out.println("❌ 添加失败");
            return "商品添加失败";
        }
    }

    public void GetGoods() {
        System.out.println("\n=== 商品列表 ===");
        List<good> goodsList = goodsMapper.getAllGoods();
        if (goodsList.isEmpty()) {
            System.out.println("暂无商品");
            return;
        }

        System.out.println("编码\t名称\t\t价格");
        System.out.println("---------------------");
        for (good g : goodsList) {
            System.out.printf("%d\t%s\t\t%.2f\n", g.getGoodsCode(), g.getGoodsName(), g.getGoodsPrice());
        }
    }

    public String DeleteGoods() {
        System.out.println("\n=== 删除商品 ===");
        System.out.print("请输入商品编码: ");
        int code = Constans.SC.nextInt();


        int count = goodsMapper.countOrdersGoodsByGoodsCode(code);
        if (count > 0) {
            throw new RuntimeException("无法删除商品，因为存在关联的订单记录");
        }
        else {
            int result = goodsMapper.deleteGoodsByCode(code);
            if (result > 0) {
                System.out.println("✅ 删除成功");
                return "商品删除成功";
            } else {
                System.out.println("❌ 删除失败");
                return "商品删除失败";
            }
        }

    }

    public String UpdateGoods() {
        System.out.println("\n=== 修改商品 ===");
        System.out.print("商品编码: ");
        int code = Constans.SC.nextInt();
        Constans.SC.nextLine();

        good existing = goodsMapper.getGoodsByCode(code);
        if (existing == null) {
            System.out.println("❌ 商品不存在");
            return "商品不存在";
        }

        System.out.print("新名称(" + existing.getGoodsName() + "): ");
        String name = Constans.SC.nextLine();
        if (!name.isEmpty()) {
            existing.setGoodsName(name);
        }

        System.out.print("新价格(" + existing.getGoodsPrice() + "): ");
        String priceStr = Constans.SC.nextLine();
        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                existing.setGoodsPrice(price);
            } catch (NumberFormatException e) {
                System.out.println("❌ 价格格式错误");
                return "修改失败";
            }
        }

        int result = goodsMapper.updateGoods(existing);
        if (result > 0) {
            System.out.println("✅ 修改成功");
            return "商品修改成功";
        } else {
            System.out.println("❌ 修改失败");
            return "商品修改失败";
        }
    }


    public String AddOrder() {
        int price;
        System.out.println("请输入商品id");
        int id = Constans.SC.nextInt();
        int quantity;
        if (isValidGoodsId(id)) {
            System.out.println("请输入商品数量");
            quantity = Constans.SC.nextInt();
            System.out.print("订单价格: ");
            price = Constans.SC.nextInt();
            if(price==quantity*goodsMapper.getGoodsByCode(id).getGoodsPrice()){
                System.out.println("✅ 商品id存在，订单添加成功");
            }else{
                System.out.println("❌ 订单价格错误，订单添加失败");
                return "订单添加失败";
            }
        } else {
            System.out.println("❌ 商品id不存在，订单添加失败");
            return "订单添加失败";
        }
        order newOrder = new order(null, LocalDateTime.now(), price);
        int result = orderMapper.insertOrder(newOrder);
        good_order good_order = new good_order(null, newOrder.getOrderCode(), id, quantity);
        ogMapper.insertOG(good_order);
        if (result > 0) {
            System.out.println("✅ 订单添加成功");
            return "订单添加成功";
        } else {
            System.out.println("❌ 订单添加失败");
            return "订单添加失败";
        }
    }

    public void GetAllOrders() {
        System.out.println("\n=== 订单列表 ===");
        List<order> ordersList = orderMapper.getAllOrders();
        if (ordersList.isEmpty()) {
            System.out.println("暂无订单");
            return;
        }
        System.out.println("编码\t\t时间\t\t\t价格\t商品名称");
        System.out.println("-----------------------------------");
        for (order o : ordersList) {
            System.out.printf("%d\t%s\t%d\t\n",
                    o.getOrderCode(),
                    o.getOrderTime().toString().substring(0, 16),
                    o.getOrderPrice());
            System.out.println(ogMapper.getGoodsNameByOrderCode(o.getOrderCode()));
        }

    }

    public String DeleteOrder() {
        System.out.println("\n=== 删除订单 ===");
        System.out.print("请输入订单编码: ");
        int code = Constans.SC.nextInt();

        int result = orderMapper.deleteOrderByCode(code);
        if (result > 0) {
            System.out.println("✅ 删除成功");
            return "订单删除成功";
        } else {
            System.out.println("❌ 删除失败");
            return "订单删除失败";
        }
    }
    @Transactional
    public String UpdateOrder() {
        System.out.println("\n=== 修改订单 ===");
        System.out.print("订单编码: ");
        int code = Constans.SC.nextInt();
        Constans.SC.nextLine();

        order existing = orderMapper.getOrderByCode(code);
        if (existing == null) {
            System.out.println("❌ 订单不存在");
            return "订单不存在";
        }

        System.out.print("新价格(" + existing.getOrderPrice() + "): ");
        String priceStr = Constans.SC.nextLine();
        if (!priceStr.isEmpty()) {
            try {
                int price = Integer.parseInt(priceStr);
                existing.setOrderPrice(price);
            } catch (NumberFormatException e) {
                System.out.println("❌ 价格格式错误");
                return "修改失败";
            }
        }

        int result = orderMapper.updateOrder(existing);
        if (result > 0) {
            System.out.println("✅ 修改成功");
            return "订单修改成功";
        } else {
            System.out.println("❌ 修改失败");
            return "订单修改失败";
        }
    }



    public String findByPage() {
        int totalRecords = goodsMapper.getTotalGoodsCount();
        if (totalRecords == 0) {
            System.out.println("---------------------");
            System.out.println("|   暂无商品数据   |");
            System.out.println("---------------------");
            return "暂无商品";
        }

        // 输入页码
        int currentPage;
        while (true) {
            System.out.print("请输入页码（1-" + (int) Math.ceil((double) totalRecords / 10) + "）：");
            try {
                currentPage = Constans.SC.nextInt();
                if (currentPage >= 1) break;
                System.out.println("页码必须大于 0！");
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字！");
                Constans.SC.nextLine();
            }
        }
        int pageSize;
        while (true) {
            System.out.print("请输入每页显示数量（1-100）：");
            try {
                pageSize = Constans.SC.nextInt();
                if (pageSize >= 1 && pageSize <= 100) break;
                System.out.println("每页数量必须在 1 到 100 之间！");
            } catch (Exception e) {
                System.out.println("输入无效，请输入数字！");
                Constans.SC.nextLine();
            }
        }
        Constans.SC.nextLine();

        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (currentPage > totalPages) {
            System.out.println("输入页码超出范围，已调整到最后一页！");
            currentPage = totalPages;
        }

        while (true) {
            List<good> goodsList = goodsMapper.getGoodsByPage((currentPage - 1) * pageSize, pageSize);
            System.out.println("-------------------------------");
            System.out.printf("| 页: %d/%d  总记录: %d  每页: %d |\n",
                    currentPage, totalPages, totalRecords, pageSize);
            System.out.println("|-----------------------------|");
            System.out.println("| 编码 | 名称          | 价格(元) |");
            System.out.println("|-----------------------------|");
            if (goodsList.isEmpty()) {
                System.out.println("|        暂无商品数据        |");
            } else {
                for (good g : goodsList) {
                    System.out.printf("| %-4d | %-12s | %8.2f |\n",
                            g.getGoodsCode(), g.getGoodsName(), g.getGoodsPrice());
                }
            }
            System.out.println("-------------------------------");
            System.out.println("\n操作选项：");
            System.out.println("1. 上一页");
            System.out.println("2. 下一页");
            System.out.println("3. 退出");
            System.out.print("请选择 (1-3)：");

            int choice;
            try {
                choice = Constans.SC.nextInt();
                Constans.SC.nextLine();
            } catch (Exception e) {
                System.out.println("输入无效，请输入 1-3 的数字！");
                Constans.SC.nextLine();
                continue;
            }

            if (choice == 1) {
                if (currentPage > 1) {
                    currentPage--;
                } else {
                    System.out.println("已经是第一页！");
                }
            } else if (choice == 2) {
                if (currentPage < totalPages) {
                    currentPage++;
                } else {
                    System.out.println("已经是最后一页！");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("无效选项，请输入 1-3！");
            }
        }
        return "分页查询完成";
    }



    public boolean isValidGoodsId(int id) {
        List<Integer> validIds = goodsMapper.getAllGoodsCode();
        return validIds.contains(id);
    }
}