package com.ptms.app;

import com.ptms.app.controller.ClientController;
import com.ptms.app.controller.ProjectController;
import com.ptms.app.controller.ProjectMemberController;
import com.ptms.app.controller.TicketManagementController;
import com.ptms.app.controller.TicketTrackingController;
import com.ptms.app.controller.UserController;
import com.ptms.app.model.Client;
import com.ptms.app.model.Project;
import com.ptms.app.model.ProjectMember;
import com.ptms.app.model.TicketManagement;
import com.ptms.app.model.TicketTracking;
import com.ptms.app.model.User;
import com.ptms.app.service.UserService;
import com.ptms.app.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER =
            Logger.getLogger(Main.class.getName());

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final UserService userService =
            new UserService();

    public static void main(String[] args) {

        LOGGER.info("PTMS application started.");

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("========================================");
            System.out.println("   PROJECT TRACKING MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    login();
                    break;

                case "2":
                    running = false;
                    LOGGER.info("PTMS application stopped.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    private static void login() {

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.getUserByEmail(email);

        if (user == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        if (!password.equals(user.getPasswordHash())) {
            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println();
        System.out.println("Login successful.");
        System.out.println("Welcome, " + user.getName());

        showRoleMenu(user);
    }

    private static void showRoleMenu(User user) {

        switch (user.getRoleId()) {

            case 1:
                showAdminMenu();
                break;

            case 2:
                showProjectManagerMenu(user);
                break;

            case 3:
                showTeamLeadMenu(user);
                break;

            case 4:
                showTeamMemberMenu(user);
                break;

            default:
                System.out.println("Unknown user role.");
        }
    }

    private static void showAdminMenu() {

        UserController userController =
                new UserController();

        ClientController clientController =
                new ClientController();

        ProjectController projectController =
                new ProjectController();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("========== ADMIN MENU ==========");
            System.out.println("1. User Management");
            System.out.println("2. Client Management");
            System.out.println("3. Project Management");
            System.out.println("4. Dashboard");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    userManagementMenu(userController);
                    break;

                case "2":
                    clientManagementMenu(clientController);
                    break;

                case "3":
                    adminProjectManagementMenu(projectController);
                    break;

                case "4":
                    showDashboard(
                            userController,
                            clientController,
                            projectController
                    );
                    break;

                case "5":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void userManagementMenu(
            UserController userController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("======= USER MANAGEMENT =======");
            System.out.println("1. Add User");
            System.out.println("2. View Users");
            System.out.println("3. Search User");
            System.out.println("4. Update User");
            System.out.println("5. Assign Role");
            System.out.println("6. Delete User");
            System.out.println("7. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addUser(userController);
                    break;

                case "2":
                    viewUsers(userController);
                    break;

                case "3":
                    searchUsers(userController);
                    break;

                case "4":
                    updateUser(userController);
                    break;

                case "5":
                    assignRole(userController);
                    break;

                case "6":
                    deleteUser(userController);
                    break;

                case "7":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addUser(
            UserController userController) {

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int roleId = readInt("Enter role ID: ");

        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRoleId(roleId);

        userController.addUser(user);

        System.out.println("User added successfully.");
    }

    private static void viewUsers(
            UserController userController) {

        List<User> users =
                userController.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void searchUsers(
            UserController userController) {

        System.out.print("Enter user name: ");
        String name = scanner.nextLine();

        List<User> users =
                userController.searchUsers(name);

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void updateUser(
            UserController userController) {

        int id = readInt("Enter user ID: ");

        User user =
                userController.getUserById(id);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter new name: ");
        user.setName(scanner.nextLine());

        System.out.print("Enter new email: ");
        user.setEmail(scanner.nextLine());

        System.out.print("Enter new password: ");
        user.setPasswordHash(scanner.nextLine());

        user.setRoleId(
                readInt("Enter new role ID: ")
        );

        userController.updateUser(user);

        System.out.println("User updated successfully.");
    }

    private static void assignRole(
            UserController userController) {

        int userId =
                readInt("Enter user ID: ");

        int roleId =
                readInt("Enter new role ID: ");

        userController.updateUserRole(
                userId,
                roleId
        );

        System.out.println("Role updated successfully.");
    }

    private static void deleteUser(
            UserController userController) {

        int id = readInt("Enter user ID: ");

        User user =
                userController.getUserById(id);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        userController.deleteUser(id);

        System.out.println("User deleted successfully.");
    }

    private static void clientManagementMenu(
            ClientController clientController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("====== CLIENT MANAGEMENT ======");
            System.out.println("1. Add Client");
            System.out.println("2. View Clients");
            System.out.println("3. Search Client");
            System.out.println("4. View Client By ID");
            System.out.println("5. View Client By Email");
            System.out.println("6. Update Client");
            System.out.println("7. Delete Client");
            System.out.println("8. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addClient(clientController);
                    break;

                case "2":
                    viewClients(clientController);
                    break;

                case "3":
                    searchClients(clientController);
                    break;

                case "4":
                    viewClientById(clientController);
                    break;

                case "5":
                    viewClientByEmail(clientController);
                    break;

                case "6":
                    updateClient(clientController);
                    break;

                case "7":
                    deleteClient(clientController);
                    break;

                case "8":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addClient(
            ClientController clientController) {

        System.out.print("Enter client name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();

        Client client = new Client();

        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        client.setCompanyName(companyName);

        clientController.addClient(client);

        System.out.println("Client added successfully.");
    }

    private static void viewClients(
            ClientController clientController) {

        List<Client> clients =
                clientController.getAllClients();

        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            return;
        }

        for (Client client : clients) {
            System.out.println(client);
        }
    }

    private static void searchClients(
            ClientController clientController) {

        System.out.print("Enter client name: ");
        String name = scanner.nextLine();

        List<Client> clients =
                clientController.searchClients(name);

        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            return;
        }

        for (Client client : clients) {
            System.out.println(client);
        }
    }

    private static void viewClientById(
            ClientController clientController) {

        int id = readInt("Enter client ID: ");

        Client client =
                clientController.getClientById(id);

        if (client == null) {
            System.out.println("Client not found.");
        } else {
            System.out.println(client);
        }
    }

    private static void viewClientByEmail(
            ClientController clientController) {

        System.out.print("Enter client email: ");
        String email = scanner.nextLine();

        Client client =
                clientController.getClientByEmail(email);

        if (client == null) {
            System.out.println("Client not found.");
        } else {
            System.out.println(client);
        }
    }

    private static void updateClient(
            ClientController clientController) {

        int id = readInt("Enter client ID: ");

        Client client =
                clientController.getClientById(id);

        if (client == null) {
            System.out.println("Client not found.");
            return;
        }

        System.out.print("Enter new name: ");
        client.setName(scanner.nextLine());

        System.out.print("Enter new email: ");
        client.setEmail(scanner.nextLine());

        System.out.print("Enter new phone: ");
        client.setPhone(scanner.nextLine());

        System.out.print("Enter new company name: ");
        client.setCompanyName(scanner.nextLine());

        clientController.updateClient(client);

        System.out.println("Client updated successfully.");
    }

    private static void deleteClient(
            ClientController clientController) {

        int id = readInt("Enter client ID: ");

        Client client =
                clientController.getClientById(id);

        if (client == null) {
            System.out.println("Client not found.");
            return;
        }

        clientController.deleteClient(id);

        System.out.println("Client deleted successfully.");
    }

    private static void adminProjectManagementMenu(
            ProjectController projectController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("===== PROJECT MANAGEMENT =====");
            System.out.println("1. View Projects");
            System.out.println("2. Search Project");
            System.out.println("3. View Project By ID");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    viewProjects(projectController);
                    break;

                case "2":
                    searchProjects(projectController);
                    break;

                case "3":
                    viewProjectById(projectController);
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void showDashboard(
            UserController userController,
            ClientController clientController,
            ProjectController projectController) {

        TicketManagementController ticketManagementController =
                new TicketManagementController();

        List<User> users =
                userController.getAllUsers();

        List<Client> clients =
                clientController.getAllClients();

        List<Project> projects =
                projectController.getAllProjects();

        List<TicketManagement> tickets =
                ticketManagementController.getAllTickets();

        System.out.println();
        System.out.println("========== ADMIN DASHBOARD ==========");
        System.out.println("Total Users    : " + users.size());
        System.out.println("Total Clients  : " + clients.size());
        System.out.println("Total Projects : " + projects.size());
        System.out.println("Total Tickets  : " + tickets.size());
    }

    private static void showProjectManagerMenu(User user) {

        ProjectController projectController =
                new ProjectController();

        ProjectMemberController projectMemberController =
                new ProjectMemberController();

        TicketManagementController ticketManagementController =
                new TicketManagementController();

        TicketTrackingController ticketTrackingController =
                new TicketTrackingController();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("====== PROJECT MANAGER MENU ======");
            System.out.println("1. Project Management");
            System.out.println("2. Ticket Management");
            System.out.println("3. Team Management");
            System.out.println("4. Project Progress");
            System.out.println("5. Approve Project Completion");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    projectManagerProjectMenu(
                            user,
                            projectController
                    );
                    break;

                case "2":
                    projectManagerTicketMenu(
                            user,
                            projectController,
                            projectMemberController,
                            ticketManagementController
                    );
                    break;

                case "3":
                    projectManagerTeamMenu(
                            user,
                            projectController,
                            projectMemberController
                    );
                    break;

                case "4":
                    projectManagerProgressMenu(
                            user,
                            projectController,
                            ticketManagementController,
                            ticketTrackingController
                    );
                    break;

                case "5":
                   approveProjectCompletion(
                           user,
                            projectController,
                            ticketManagementController
                    );
                   break;

                case "6":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void projectManagerProjectMenu(
            User user,
            ProjectController projectController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("===== PROJECT MANAGEMENT =====");
            System.out.println("1. Create Project");
            System.out.println("2. View My Projects");
            System.out.println("3. Assign Project To Team Lead");
            System.out.println("4. View Project By ID");
            System.out.println("5. Search Project");
            System.out.println("6. Update Project");
            System.out.println("7. Delete Project");
            System.out.println("8. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addProject(
                            user,
                            projectController
                    );
                    break;

                case "2":
                    viewMyProjects(
                            user,
                            projectController
                    );
                    break;

                case "3":
                    assignProjectToTeamLead(
                            user,
                            projectController
                    );
                    break;

                case "4":
                    viewProjectById(projectController);
                    break;

                case "5":
                    searchProjects(projectController);
                    break;

                case "6":
                    updateProject(
                            user,
                            projectController
                    );
                    break;

                case "7":
                    deleteProject(
                            user,
                            projectController
                    );
                    break;

                case "8":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProject(
            User user,
            ProjectController projectController) {

        System.out.print("Enter project name: ");
        String name = scanner.nextLine();

        System.out.print("Enter requirements: ");
        String requirements = scanner.nextLine();

        int teamLeadId =
                readInt("Enter team lead ID: ");

        int clientId =
                readInt("Enter client ID: ");

        System.out.print("Enter domain: ");
        String domain = scanner.nextLine();

        BigDecimal cost =
                readBigDecimal("Enter cost: ");

        int teamSize =
                readInt("Enter team size: ");

        LocalDate startDate =
                readDate(
                        "Enter start date (yyyy-MM-dd): "
                );

        LocalDate deadline =
                readDate(
                        "Enter deadline (yyyy-MM-dd): "
                );

        System.out.print("Enter priority: ");
        String priority = scanner.nextLine();

        Project project = new Project();

        project.setName(name);
        project.setRequirements(requirements);
        project.setManagerId(user.getId());
        project.setTeamLeadId(teamLeadId);
        project.setClientId(clientId);
        project.setDomain(domain);
        project.setCost(cost);
        project.setTeamSize(teamSize);
        project.setStartDate(startDate);
        project.setDeadline(deadline);
        project.setPriority(priority);
        project.setStatus("PLANNED");

        projectController.addProject(project);

        System.out.println(
                "Project created successfully."
        );
    }

    private static void assignProjectToTeamLead(
            User user,
            ProjectController projectController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can assign only your projects."
            );
            return;
        }

        int teamLeadId =
                readInt("Enter team lead ID: ");

        project.setTeamLeadId(teamLeadId);

        projectController.updateProject(project);

        System.out.println(
                "Project assigned to team lead successfully."
        );
    }

    private static void viewProjects(
            ProjectController projectController) {

        List<Project> projects =
                projectController.getAllProjects();

        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }

        for (Project project : projects) {
            System.out.println(project);
        }
    }

    private static void viewMyProjects(
            User user,
            ProjectController projectController) {

        List<Project> projects =
                projectController.getAllProjects();

        boolean found = false;

        for (Project project : projects) {

            if (project.getManagerId() == user.getId()) {
                System.out.println(project);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No projects found.");
        }
    }

    private static void viewProjectById(
            ProjectController projectController) {

        int id =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(id);

        if (project == null) {
            System.out.println("Project not found.");
        } else {
            System.out.println(project);
        }
    }

    private static void searchProjects(
            ProjectController projectController) {

        System.out.print("Enter project name: ");
        String name = scanner.nextLine();

        List<Project> projects =
                projectController.searchProjects(name);

        if (projects.isEmpty()) {
            System.out.println("No projects found.");
            return;
        }

        for (Project project : projects) {
            System.out.println(project);
        }
    }

    private static void updateProject(
            User user,
            ProjectController projectController) {

        int id =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(id);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can update only your projects."
            );
            return;
        }

        System.out.print("Enter new project name: ");
        project.setName(scanner.nextLine());

        System.out.print("Enter new requirements: ");
        project.setRequirements(scanner.nextLine());

        System.out.print("Enter new team lead ID: ");
        project.setTeamLeadId(
                readInt("")
        );

        System.out.print("Enter new client ID: ");
        project.setClientId(
                readInt("")
        );

        System.out.print("Enter new domain: ");
        project.setDomain(scanner.nextLine());

        project.setCost(
                readBigDecimal("Enter new cost: ")
        );

        project.setTeamSize(
                readInt("Enter new team size: ")
        );

        project.setStartDate(
                readDate(
                        "Enter new start date (yyyy-MM-dd): "
                )
        );

        project.setDeadline(
                readDate(
                        "Enter new deadline (yyyy-MM-dd): "
                )
        );

        System.out.print("Enter new priority: ");
        project.setPriority(scanner.nextLine());

        System.out.print("Enter new status: ");
        project.setStatus(scanner.nextLine());

        projectController.updateProject(project);

        System.out.println(
                "Project updated successfully."
        );
    }

    private static void deleteProject(
            User user,
            ProjectController projectController) {

        int id =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(id);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can delete only your projects."
            );
            return;
        }

        projectController.deleteProject(id);

        System.out.println(
                "Project deleted successfully."
        );
    }

    private static void projectManagerTeamMenu(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("======= TEAM MANAGEMENT =======");
            System.out.println("1. Add Member");
            System.out.println("2. View Project Members");
            System.out.println("3. View All Project Members");
            System.out.println("4. Remove Member");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addProjectMember(
                            user,
                            projectController,
                            projectMemberController
                    );
                    break;

                case "2":
                    viewProjectMembers(
                            projectMemberController
                    );
                    break;

                case "3":
                    viewAllProjectMembers(
                            projectMemberController
                    );
                    break;

                case "4":
                    removeProjectMember(
                            projectMemberController
                    );
                    break;

                case "5":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addProjectMember(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can manage members only for your projects."
            );
            return;
        }

        int userId =
                readInt("Enter user ID: ");

        ProjectMember projectMember =
                new ProjectMember();

        projectMember.setProjectId(projectId);
        projectMember.setUserId(userId);

        projectMemberController.addProjectMember(
                projectMember
        );

        System.out.println(
                "Project member added successfully."
        );
    }

    private static void viewProjectMembers(
            ProjectMemberController projectMemberController) {

        int projectId =
                readInt("Enter project ID: ");

        List<ProjectMember> members =
                projectMemberController
                        .getMembersByProjectId(projectId);

        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (ProjectMember member : members) {
            System.out.println(member);
        }
    }

    private static void viewAllProjectMembers(
            ProjectMemberController projectMemberController) {

        List<ProjectMember> members =
                projectMemberController
                        .getAllProjectMembers();

        if (members.isEmpty()) {
            System.out.println(
                    "No project members found."
            );
            return;
        }

        for (ProjectMember member : members) {
            System.out.println(member);
        }
    }

    private static void removeProjectMember(
            ProjectMemberController projectMemberController) {

        int projectId =
                readInt("Enter project ID: ");

        int userId =
                readInt("Enter user ID: ");

        projectMemberController.deleteProjectMember(
                projectId,
                userId
        );

        System.out.println(
                "Project member removed successfully."
        );
    }

    private static void projectManagerTicketMenu(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController,
            TicketManagementController ticketManagementController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("======== TICKET MANAGEMENT ========");
            System.out.println("1. Create Ticket");
            System.out.println("2. View Tickets");
            System.out.println("3. Search By Status");
            System.out.println("4. Search By Priority");
            System.out.println("5. Search By Assigned Member");
            System.out.println("6. Update Ticket");
            System.out.println("7. Delete Ticket");
            System.out.println("8. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addTicket(
                            user,
                            projectController,
                            projectMemberController,
                            ticketManagementController
                    );
                    break;

                case "2":
                    viewTickets(
                            ticketManagementController
                    );
                    break;

                case "3":
                    searchTicketsByStatus(
                            ticketManagementController
                    );
                    break;

                case "4":
                    searchTicketsByPriority(
                            ticketManagementController
                    );
                    break;

                case "5":
                    searchTicketsByAssignedMember(
                            ticketManagementController
                    );
                    break;

                case "6":
                    updateTicket(
                            user,
                            projectController,
                            projectMemberController,
                            ticketManagementController
                    );
                    break;

                case "7":
                    deleteTicket(
                            ticketManagementController
                    );
                    break;

                case "8":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addTicket(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController,
            TicketManagementController ticketManagementController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can add tickets only to your projects."
            );
            return;
        }

        int assignedTo =
                readInt("Enter assigned user ID: ");

        List<ProjectMember> members =
                projectMemberController
                        .getMembersByProjectId(projectId);

        boolean memberFound = false;

        for (ProjectMember member : members) {

            if (member.getUserId() == assignedTo) {
                memberFound = true;
                break;
            }
        }

        if (!memberFound) {
            System.out.println(
                    "Assigned user is not a member of the project."
            );
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter priority: ");
        String priority = scanner.nextLine();

        LocalDate deadline =
                readDate(
                        "Enter deadline (yyyy-MM-dd): "
                );

        TicketManagement ticket =
                new TicketManagement();

        ticket.setProjectId(projectId);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setDeadline(deadline);
        ticket.setAssignedTo(assignedTo);
        ticket.setStatus("IN_DEVELOPMENT");

        ticketManagementController.addTicket(ticket);

        System.out.println(
                "Ticket created successfully."
        );
    }

    private static void viewTickets(
            TicketManagementController ticketManagementController) {

        List<TicketManagement> tickets =
                ticketManagementController.getAllTickets();

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (TicketManagement ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void searchTicketsByStatus(
            TicketManagementController ticketManagementController) {

        System.out.print("Enter status: ");
        String status = scanner.nextLine();

        List<TicketManagement> tickets =
                ticketManagementController
                        .searchTicketsByStatus(status);

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (TicketManagement ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void searchTicketsByPriority(
            TicketManagementController ticketManagementController) {

        System.out.print("Enter priority: ");
        String priority = scanner.nextLine();

        List<TicketManagement> tickets =
                ticketManagementController
                        .searchTicketsByPriority(priority);

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (TicketManagement ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void searchTicketsByAssignedMember(
            TicketManagementController ticketManagementController) {

        int userId =
                readInt("Enter assigned user ID: ");

        List<TicketManagement> tickets =
                ticketManagementController
                        .searchTicketsByAssignedTo(userId);

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
            return;
        }

        for (TicketManagement ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void updateTicket(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController,
            TicketManagementController ticketManagementController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketManagement ticket =
                ticketManagementController
                        .getTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        Project project =
                projectController
                        .getProjectById(
                                ticket.getProjectId()
                        );

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can update only tickets from your projects."
            );
            return;
        }

        int assignedTo =
                readInt("Enter new assigned user ID: ");

        List<ProjectMember> members =
                projectMemberController
                        .getMembersByProjectId(
                                ticket.getProjectId()
                        );

        boolean memberFound = false;

        for (ProjectMember member : members) {

            if (member.getUserId() == assignedTo) {
                memberFound = true;
                break;
            }
        }

        if (!memberFound) {
            System.out.println(
                    "Assigned user is not a project member."
            );
            return;
        }

        System.out.print("Enter new title: ");
        ticket.setTitle(scanner.nextLine());

        System.out.print("Enter new description: ");
        ticket.setDescription(scanner.nextLine());

        System.out.print("Enter new priority: ");
        ticket.setPriority(scanner.nextLine());

        ticket.setDeadline(
                readDate(
                        "Enter new deadline (yyyy-MM-dd): "
                )
        );

        ticket.setAssignedTo(assignedTo);

        System.out.print("Enter new status: ");
        ticket.setStatus(scanner.nextLine());

        ticketManagementController.updateTicket(ticket);

        System.out.println(
                "Ticket updated successfully."
        );
    }

    private static void deleteTicket(
            TicketManagementController ticketManagementController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketManagement ticket =
                ticketManagementController
                        .getTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        ticketManagementController.deleteTicket(ticketId);

        System.out.println(
                "Ticket deleted successfully."
        );
    }

    private static void projectManagerProgressMenu(
            User user,
            ProjectController projectController,
            TicketManagementController ticketManagementController,
            TicketTrackingController ticketTrackingController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can view progress only for your projects."
            );
            return;
        }

        List<TicketManagement> tickets =
                ticketManagementController.getAllTickets();

        int total = 0;
        int completed = 0;
        int progressSum = 0;

        for (TicketManagement ticket : tickets) {

            if (ticket.getProjectId() == projectId) {

                total++;

                TicketTracking tracking =
                        ticketTrackingController
                                .getTrackingByTicketId(
                                        ticket.getId()
                                );

                if (tracking != null) {

                    progressSum +=
                            tracking.getProgress();

                    if ("COMPLETED".equals(
                            tracking.getStatus())) {

                        completed++;
                    }
                }
            }
        }

        System.out.println();
        System.out.println(
                "======== PROJECT PROGRESS ========"
        );
        System.out.println(
                "Project : " + project.getName()
        );
        System.out.println(
                "Tickets : " + total
        );
        System.out.println(
                "Completed Tickets : " + completed
        );

        if (total > 0) {

            double progress =
                    (double) progressSum / total;

            System.out.println(
                    "Average Progress : " +
                            progress +
                            "%"
            );

        } else {

            System.out.println(
                    "Average Progress : 0%"
            );
        }
    }
    private static void approveProjectCompletion(
            User user,
            ProjectController projectController,
            TicketManagementController ticketManagementController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getManagerId() != user.getId()) {
            System.out.println(
                    "You can approve only your projects."
            );
            return;
        }

        List<TicketManagement> tickets =
                ticketManagementController.getAllTickets();

        int total = 0;
        int completed = 0;

        for (TicketManagement ticket : tickets) {

            if (ticket.getProjectId() == projectId) {

                total++;

                if ("COMPLETED".equals(ticket.getStatus())) {
                    completed++;
                }
            }
        }

        if (total == 0) {
            System.out.println(
                    "Project cannot be completed because it has no tickets."
            );
            return;
        }

        if (completed != total) {
            System.out.println(
                    "Project cannot be completed. All tickets must be completed."
            );
            return;
        }

        project.setStatus("COMPLETED");

        projectController.updateProject(project);

        System.out.println(
                "Project completion approved successfully."
        );
    }

    private static void showTeamLeadMenu(User user) {

        ProjectController projectController =
                new ProjectController();

        ProjectMemberController projectMemberController =
                new ProjectMemberController();

        TicketManagementController ticketManagementController =
                new TicketManagementController();

        TicketTrackingController ticketTrackingController =
                new TicketTrackingController();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("========= TEAM LEAD MENU =========");
            System.out.println("1. Project Members");
            System.out.println("2. Ticket Management");
            System.out.println("3. Ticket Tracking");
            System.out.println("4. Review Implemented Ticket");
            System.out.println("5. Project Progress");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    teamLeadMemberMenu(
                            user,
                            projectController,
                            projectMemberController
                    );
                    break;

                case "2":
                    teamLeadTicketMenu(
                            user,
                            projectController,
                            projectMemberController,
                            ticketManagementController
                    );
                    break;

                case "3":
                    teamLeadTrackingMenu(
                            ticketTrackingController
                    );
                    break;

                case "4":
                    reviewTicket(
                            user,
                            projectController,
                            ticketManagementController,
                            ticketTrackingController
                    );
                    break;

                case "5":
                    teamLeadProgress(
                            user,
                            projectController,
                            ticketManagementController,
                            ticketTrackingController
                    );
                    break;

                case "6":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void teamLeadMemberMenu(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("======= PROJECT MEMBERS =======");
            System.out.println("1. View Project Members");
            System.out.println("2. Add Member");
            System.out.println("3. Remove Member");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    viewProjectMembers(
                            projectMemberController
                    );
                    break;

                case "2":
                    addTeamLeadMember(
                            user,
                            projectController,
                            projectMemberController
                    );
                    break;

                case "3":
                    removeProjectMember(
                            projectMemberController
                    );
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addTeamLeadMember(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController
                        .getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getTeamLeadId() != user.getId()) {
            System.out.println(
                    "You can manage only projects assigned to you."
            );
            return;
        }

        int userId =
                readInt("Enter employee user ID: ");

        User employee =
                userService.getUserById(userId);

        if (employee == null) {
            System.out.println("User not found.");
            return;
        }

        if (employee.getRoleId() != 4) {
            System.out.println(
                    "Only employees can be added to the project."
            );
            return;
        }

        ProjectMember member =
                new ProjectMember();

        member.setProjectId(projectId);
        member.setUserId(userId);

        projectMemberController.addProjectMember(
                member
        );

        System.out.println(
                "Employee added to project successfully."
        );
    }

    private static void teamLeadTicketMenu(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController,
            TicketManagementController ticketManagementController) {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("======== TICKET MANAGEMENT ========");
            System.out.println("1. Create Ticket");
            System.out.println("2. View Tickets");
            System.out.println("3. Update Ticket Assignment");
            System.out.println("4. Search By Status");
            System.out.println("5. Search By Priority");
            System.out.println("6. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    addTeamLeadTicket(
                            user,
                            projectController,
                            projectMemberController,
                            ticketManagementController
                    );
                    break;

                case "2":
                    viewTeamLeadTickets(
                            user,
                            projectController,
                            ticketManagementController
                    );
                    break;

                case "3":
                    updateTeamLeadTicket(
                            user,
                            projectController,
                            projectMemberController,
                            ticketManagementController
                    );
                    break;

                case "4":
                    searchTicketsByStatus(
                            ticketManagementController
                    );
                    break;

                case "5":
                    searchTicketsByPriority(
                            ticketManagementController
                    );
                    break;

                case "6":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void addTeamLeadTicket(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController,
            TicketManagementController ticketManagementController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getTeamLeadId() != user.getId()) {
            System.out.println(
                    "You can create tickets only for your projects."
            );
            return;
        }

        int assignedTo =
                readInt("Enter employee user ID: ");

        User employee =
                userService.getUserById(assignedTo);

        if (employee == null) {
            System.out.println("User not found.");
            return;
        }

        if (employee.getRoleId() != 4) {
            System.out.println(
                    "Ticket can only be assigned to an employee."
            );
            return;
        }

        List<ProjectMember> members =
                projectMemberController
                        .getMembersByProjectId(projectId);

        boolean memberFound = false;

        for (ProjectMember member : members) {

            if (member.getUserId() == assignedTo) {
                memberFound = true;
                break;
            }
        }

        if (!memberFound) {
            System.out.println(
                    "Employee is not a member of this project."
            );
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter priority: ");
        String priority = scanner.nextLine();

        LocalDate deadline =
                readDate(
                        "Enter deadline (yyyy-MM-dd): "
                );

        TicketManagement ticket =
                new TicketManagement();

        ticket.setProjectId(projectId);
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setPriority(priority);
        ticket.setDeadline(deadline);
        ticket.setAssignedTo(assignedTo);
        ticket.setStatus("IN_DEVELOPMENT");

        ticketManagementController.addTicket(ticket);

        System.out.println(
                "Ticket created and assigned successfully."
        );
    }

    private static void viewTeamLeadTickets(
            User user,
            ProjectController projectController,
            TicketManagementController ticketManagementController) {

        List<TicketManagement> tickets =
                ticketManagementController.getAllTickets();

        boolean found = false;

        for (TicketManagement ticket : tickets) {

            Project project =
                    projectController.getProjectById(
                            ticket.getProjectId()
                    );

            if (project != null &&
                    project.getTeamLeadId() == user.getId()) {

                System.out.println(ticket);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tickets found.");
        }
    }

    private static void updateTeamLeadTicket(
            User user,
            ProjectController projectController,
            ProjectMemberController projectMemberController,
            TicketManagementController ticketManagementController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketManagement ticket =
                ticketManagementController
                        .getTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        Project project =
                projectController.getProjectById(
                        ticket.getProjectId()
                );

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getTeamLeadId() != user.getId()) {
            System.out.println(
                    "You can update only tickets from your projects."
            );
            return;
        }

        int assignedTo =
                readInt("Enter new assigned employee ID: ");

        User employee =
                userService.getUserById(assignedTo);

        if (employee == null ||
                employee.getRoleId() != 4) {

            System.out.println(
                    "Invalid employee ID."
            );
            return;
        }

        List<ProjectMember> members =
                projectMemberController
                        .getMembersByProjectId(
                                ticket.getProjectId()
                        );

        boolean memberFound = false;

        for (ProjectMember member : members) {

            if (member.getUserId() == assignedTo) {
                memberFound = true;
                break;
            }
        }

        if (!memberFound) {
            System.out.println(
                    "Employee is not a member of this project."
            );
            return;
        }

        ticket.setAssignedTo(assignedTo);

        System.out.print("Enter new priority: ");
        ticket.setPriority(scanner.nextLine());

        ticket.setDeadline(
                readDate(
                        "Enter new deadline (yyyy-MM-dd): "
                )
        );

        ticketManagementController.updateTicket(ticket);

        System.out.println(
                "Ticket assignment updated successfully."
        );
    }

    private static void teamLeadTrackingMenu(
            TicketTrackingController ticketTrackingController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketTracking tracking =
                ticketTrackingController
                        .getTrackingByTicketId(ticketId);

        if (tracking == null) {
            System.out.println(
                    "No tracking information found."
            );
        } else {
            System.out.println(tracking);
        }
    }

    private static void reviewTicket(
            User user,
            ProjectController projectController,
            TicketManagementController ticketManagementController,
            TicketTrackingController ticketTrackingController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketManagement ticket =
                ticketManagementController
                        .getTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        Project project =
                projectController.getProjectById(
                        ticket.getProjectId()
                );

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getTeamLeadId() != user.getId()) {
            System.out.println(
                    "You can review only tickets from your projects."
            );
            return;
        }

        TicketTracking tracking =
                ticketTrackingController
                        .getTrackingByTicketId(ticketId);

        if (tracking == null) {
            System.out.println(
                    "No tracking information found."
            );
            return;
        }

        if (!"IMPLEMENTED".equals(
                tracking.getStatus())) {

            System.out.println(
                    "Ticket is not ready for review."
            );
            return;
        }

        System.out.println("1. Mark Completed");
        System.out.println("2. Send Back To Development");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine();

        if ("1".equals(choice)) {

            if (tracking.getProgress() != 100) {
                System.out.println(
                        "Ticket must have 100% progress."
                );
                return;
            }

            tracking.setStatus("COMPLETED");
            ticket.setStatus("COMPLETED");

            ticketTrackingController.updateTracking(
                    tracking
            );

            ticketManagementController.updateTicket(
                    ticket
            );

            System.out.println(
                    "Ticket marked as completed."
            );

        } else if ("2".equals(choice)) {

            tracking.setStatus("IN_DEVELOPMENT");
            ticket.setStatus("IN_DEVELOPMENT");

            ticketTrackingController.updateTracking(
                    tracking
            );

            ticketManagementController.updateTicket(
                    ticket
            );

            System.out.println(
                    "Ticket sent back to development."
            );

        } else {

            System.out.println("Invalid choice.");
        }
    }

    private static void teamLeadProgress(
            User user,
            ProjectController projectController,
            TicketManagementController ticketManagementController,
            TicketTrackingController ticketTrackingController) {

        int projectId =
                readInt("Enter project ID: ");

        Project project =
                projectController.getProjectById(projectId);

        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        if (project.getTeamLeadId() != user.getId()) {
            System.out.println(
                    "You can view only projects assigned to you."
            );
            return;
        }

        List<TicketManagement> tickets =
                ticketManagementController.getAllTickets();

        int total = 0;
        int completed = 0;
        int progressSum = 0;

        for (TicketManagement ticket : tickets) {

            if (ticket.getProjectId() == projectId) {

                total++;

                TicketTracking tracking =
                        ticketTrackingController
                                .getTrackingByTicketId(
                                        ticket.getId()
                                );

                if (tracking != null) {

                    progressSum +=
                            tracking.getProgress();

                    if ("COMPLETED".equals(
                            tracking.getStatus())) {

                        completed++;
                    }
                }
            }
        }

        System.out.println();
        System.out.println(
                "======== PROJECT PROGRESS ========"
        );
        System.out.println(
                "Project : " + project.getName()
        );
        System.out.println(
                "Tickets : " + total
        );
        System.out.println(
                "Completed Tickets : " + completed
        );

        if (total > 0) {

            double progress =
                    (double) progressSum / total;

            System.out.println(
                    "Average Progress : " +
                            progress +
                            "%"
            );

        } else {

            System.out.println(
                    "Average Progress : 0%"
            );
        }
    }

    private static void showTeamMemberMenu(User user) {

        TicketManagementController ticketManagementController =
                new TicketManagementController();

        TicketTrackingController ticketTrackingController =
                new TicketTrackingController();

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("======== TEAM MEMBER MENU ========");
            System.out.println("1. View Assigned Tickets");
            System.out.println("2. Update Ticket");
            System.out.println("3. Ticket Tracking");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    viewAssignedTickets(
                            user,
                            ticketManagementController
                    );
                    break;

                case "2":
                    updateTeamMemberTicket(
                            user,
                            ticketManagementController,
                            ticketTrackingController
                    );
                    break;

                case "3":
                    viewTeamMemberTracking(
                            user,
                            ticketTrackingController,
                            ticketManagementController
                    );
                    break;

                case "4":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewAssignedTickets(
            User user,
            TicketManagementController ticketManagementController) {

        List<TicketManagement> tickets =
                ticketManagementController
                        .searchTicketsByAssignedTo(
                                user.getId()
                        );

        if (tickets.isEmpty()) {
            System.out.println(
                    "No assigned tickets found."
            );
            return;
        }

        for (TicketManagement ticket : tickets) {
            System.out.println(ticket);
        }
    }

    private static void updateTeamMemberTicket(
            User user,
            TicketManagementController ticketManagementController,
            TicketTrackingController ticketTrackingController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketManagement ticket =
                ticketManagementController
                        .getTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (ticket.getAssignedTo() != user.getId()) {
            System.out.println(
                    "You are not assigned to this ticket."
            );
            return;
        }

        System.out.print("Enter status: ");
        String status = scanner.nextLine();

        int progress =
                readInt("Enter progress: ");

        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        try {
            TicketTracking tracking =
                    ticketTrackingController
                            .getTrackingByTicketId(ticketId);

            tracking.setUpdatedBy(user.getId());
            tracking.setStatus(status);
            tracking.setProgress(progress);
            tracking.setComment(comment);

            ticketTrackingController.updateTracking(tracking);

        } catch (ResourceNotFoundException e) {

            TicketTracking tracking =
                    new TicketTracking();

            tracking.setTicketId(ticketId);
            tracking.setUpdatedBy(user.getId());
            tracking.setStatus(status);
            tracking.setProgress(progress);
            tracking.setComment(comment);

            ticketTrackingController.addTracking(tracking);
        }

        ticket.setStatus(status);

        ticketManagementController.updateTicket(ticket);

        System.out.println(
                "Ticket update completed."
        );
    }

    private static void viewTeamMemberTracking(
            User user,
            TicketTrackingController ticketTrackingController,
            TicketManagementController ticketManagementController) {

        int ticketId =
                readInt("Enter ticket ID: ");

        TicketManagement ticket =
                ticketManagementController
                        .getTicketById(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found.");
            return;
        }

        if (ticket.getAssignedTo() != user.getId()) {
            System.out.println(
                    "You are not assigned to this ticket."
            );
            return;
        }

        TicketTracking tracking =
                ticketTrackingController
                        .getTrackingByTicketId(ticketId);

        if (tracking == null) {
            System.out.println(
                    "No tracking information found."
            );
        } else {
            System.out.println(tracking);
        }
    }

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    private static BigDecimal readBigDecimal(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return new BigDecimal(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid amount."
                );
            }
        }
    }

    private static LocalDate readDate(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return LocalDate.parse(
                        scanner.nextLine()
                );

            } catch (Exception e) {

                System.out.println(
                        "Please enter date in yyyy-MM-dd format."
                );
            }
        }
    }
}