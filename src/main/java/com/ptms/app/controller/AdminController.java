package com.ptms.app.controller;

import com.ptms.app.model.Admin;
import com.ptms.app.service.AdminService;

import java.util.logging.Logger;

public class AdminController {

    private static final Logger LOGGER =
            Logger.getLogger(AdminController.class.getName());

    private final AdminService adminService;

    public AdminController() {
        adminService = new AdminService();
    }

    public void addAdmin(Admin admin) {
        adminService.addAdmin(admin);
        LOGGER.info("Admin add operation completed.");
    }

    public Admin getAdminById(int id) {
        return adminService.getAdminById(id);
    }

    public Admin getAdminByUserId(int userId) {
        return adminService.getAdminByUserId(userId);
    }

    public void updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);
        LOGGER.info("Admin update operation completed.");
    }

    public void deleteAdmin(int id) {
        adminService.deleteAdmin(id);
        LOGGER.info("Admin delete operation completed.");
    }
}