package controller;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dao.HibernateUtil;
import model.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

import service.TVRadioServiceImpl;
import service.Impl.UserServiceImpl;
import service.Impl.ChannelServiceImpl;
import service.Impl.ProgramServiceImpl;
import service.Impl.ProgramScheduleServiceImpl;
import service.Impl.EmployeeServiceImpl;
import service.Impl.AdvertisementServiceImpl;
import service.Impl.ExpenseServiceImpl;
import service.Impl.FinancialReportServiceImpl;
import service.Impl.ProgramAssignmentServiceImpl;
import service.Impl.EquipmentServiceImpl;

public class TVRadioServer {
    public static void main(String[] args) {
        try {
            System.out.println("Initializing Database...");
            // Initialize Hibernate
            HibernateUtil.getSessionFactory();

            // Create default users if needed
            createDefaultUsers();

            int port = resolvePort(args);
            System.out.println("Starting Server on port " + port + "...");

            Registry registry = LocateRegistry.createRegistry(port);

            // Bind services
            registry.rebind("TVRadioService", new TVRadioServiceImpl());
            registry.rebind("UserService", new UserServiceImpl());
            registry.rebind("ChannelService", new ChannelServiceImpl());
            registry.rebind("ProgramService", new ProgramServiceImpl());
            registry.rebind("ProgramScheduleService", new ProgramScheduleServiceImpl());
            registry.rebind("EmployeeService", new EmployeeServiceImpl());
            registry.rebind("AdvertisementService", new AdvertisementServiceImpl());
            registry.rebind("ExpenseService", new ExpenseServiceImpl());
            registry.rebind("FinancialReportService", new FinancialReportServiceImpl());
            registry.rebind("ProgramAssignmentService", new ProgramAssignmentServiceImpl());
            registry.rebind("EquipmentService", new EquipmentServiceImpl());

            System.out.println("Server is running on port " + port + " with per-entity services bound.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int resolvePort(String[] args) {
        int defaultPort = 3001;
        int min = 3000, max = 4000;
        if (args != null && args.length > 0) {
            String a0 = args[0];
            try {
                int candidate;
                if (a0.startsWith("--port=")) {
                    candidate = Integer.parseInt(a0.substring("--port=".length()).trim());
                } else {
                    candidate = Integer.parseInt(a0.trim());
                }
                if (candidate < min || candidate > max) {
                    throw new IllegalArgumentException("Port must be in range [" + min + "," + max + "]");
                }
                return candidate;
            } catch (RuntimeException ignored) {
                System.out.println("Invalid port argument, falling back to default " + defaultPort);
            }
        }
        return defaultPort;
    }

    private static void createDefaultUsers() {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            Long count = (Long) session.createQuery("select count(*) from User").uniqueResult();
            if (count == 0) {
                // Constructor: userId, name, username, password, role, email, phone
                User admin = new User(0, "Administrator", "admin", "admin123", "Admin", "vianneynsabimana82@gmail.com",
                        "1234567890");
                User emp = new User(0, "Employee User", "employee", "123", "Employee", "employee@email.com",
                        "0987654321");
                session.save(admin);
                session.save(emp);
                System.out.println("Default users created: admin/admin123, employee/123");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
