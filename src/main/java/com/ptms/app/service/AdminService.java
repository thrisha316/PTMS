package com.ptms.app.service;

import com.ptms.app.dao.AdminDAO;
import com.ptms.app.model.Admin;

import java.util.logging.Logger;

public class AdminService {

    private static final Logger LOGGER =
            Logger.getLogger(AdminService.class.getName());

    private final AdminDAO adminDAO;

    public AdminService() {
        adminDAO = new AdminDAO();
    }

    public void addAdmin(Admin admin) {
        adminDAO.addAdmin(admin);
        LOGGER.info("Admin added through service.");
    }

    public Admin getAdminById(int id) {
        return adminDAO.getAdminById(id);
    }

    public Admin getAdminByUserId(int userId) {
        return adminDAO.getAdminByUserId(userId);
    }

    public void updateAdmin(Admin admin) {
        adminDAO.updateAdmin(admin);
        LOGGER.info("Admin updated through service. ID: "
                + admin.getId());
    }

    public void deleteAdmin(int id) {
        adminDAO.deleteAdmin(id);
        LOGGER.info("Admin deleted through service. ID: " + id);
    }
}