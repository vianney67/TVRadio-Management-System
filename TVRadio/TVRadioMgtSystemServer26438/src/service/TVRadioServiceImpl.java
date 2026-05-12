package service;

import dao.HibernateUtil;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

public class TVRadioServiceImpl extends UnicastRemoteObject implements TVRadioService {

    public TVRadioServiceImpl() throws RemoteException {
        super();
    }

    // Helper for saving
    private void save(Object obj) throws RemoteException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw new RemoteException("Database Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Helper for list
    private <T> List<T> list(Class<T> clazz) throws RemoteException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from " + clazz.getName()).list();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Database Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Helper for delete
    private void delete(Class<?> clazz, int id) throws RemoteException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Object obj = session.get(clazz, id);
            if (obj != null)
                session.delete(obj);
            tx.commit();
        } catch (Exception e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
            throw new RemoteException("Database Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // Helper for get by id
    private <T> T get(Class<T> clazz, int id) throws RemoteException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (T) session.get(clazz, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Database Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public String loginRequest(String username, String password) throws RemoteException {
        org.hibernate.Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            System.out.println("Login request for username=" + username);
            Query q = session.createQuery("from User where username = :u and password = :p");
            q.setParameter("u", username);
            q.setParameter("p", password);
            User user = (User) q.uniqueResult();

            if (user != null) {
                // Generate OTP
                String otp = String.valueOf(100000 + new java.util.Random().nextInt(900000));

                // Store OTP
                service.util.SessionManager.getInstance().storeOTP(username, otp);

                // Send Notification
                service.util.NotificationService ns = new service.util.ActiveMQNotificationService();

                // FORCE SEND to the requested email for this task
                ns.sendEmail("vianneynsabimana82@gmail.com", "TVRadio Login OTP", "Your OTP is: " + otp);

                boolean sent = true;
                // Also send to User's registered email if different
                if (user.getEmail() != null && !user.getEmail().isEmpty()
                        && !user.getEmail().equals("vianneynsabimana82@gmail.com")) {
                    ns.sendEmail(user.getEmail(), "TVRadio Login OTP", "Your OTP is: " + otp);
                }
                if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                    ns.sendSMS(user.getPhoneNumber(), "Your OTP is: " + otp);
                }

                return "OTP_SENT";
            }
            return "FAILED";
        } catch (Exception e) {
            e.printStackTrace();
            String causeMsg = (e.getCause() != null) ? e.getCause().getMessage() : "";
            return "ERROR: " + e.getMessage() + " [Cause: " + causeMsg + "]";
        } finally {
            session.close();
        }
    }

    @Override
    public model.Session loginConfirm(String username, String otp) throws RemoteException {
        String storedOtp = service.util.SessionManager.getInstance().getOTP(username);
        if (storedOtp != null && storedOtp.equals(otp)) {
            // Verify User again to get details
            org.hibernate.Session hSession = HibernateUtil.getSessionFactory().openSession();
            try {
                Query q = hSession.createQuery("from User where username = :u");
                q.setParameter("u", username);
                User user = (User) q.uniqueResult();
                if (user != null) {
                    // Create Session
                    String sessionId = java.util.UUID.randomUUID().toString();
                    model.Session userSession = new model.Session(sessionId, user.getUserId(), user.getUsername(),
                            user.getRole(), new java.util.Date());
                    service.util.SessionManager.getInstance().addSession(userSession);
                    service.util.SessionManager.getInstance().clearOTP(username);
                    return userSession;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RemoteException("Database Error: " + e.getMessage());
            } finally {
                hSession.close();
            }
        }
        return null; // Invalid OTP
    }

    @Override
    public void logout(String sessionId) throws RemoteException {
        service.util.SessionManager.getInstance().removeSession(sessionId);
    }

    @Override
    public void registerUser(User user) throws RemoteException {
        save(user);
    }

    @Override
    public void addChannel(Channel channel) throws RemoteException {
        save(channel);
    }

    @Override
    public void updateChannel(Channel channel) throws RemoteException {
        save(channel);
    }

    @Override
    public void deleteChannel(int id) throws RemoteException {
        delete(Channel.class, id);
    }

    @Override
    public List<Channel> getAllChannels() throws RemoteException {
        return list(Channel.class);
    }

    @Override
    public void addProgram(Program program) throws RemoteException {
        save(program);
    }

    @Override
    public void updateProgram(Program program) throws RemoteException {
        save(program);
    }

    @Override
    public void deleteProgram(int id) throws RemoteException {
        delete(Program.class, id);
    }

    @Override
    public Program getProgramById(int id) throws RemoteException {
        return get(Program.class, id);
    }

    @Override
    public List<Program> getAllPrograms() throws RemoteException {
        return list(Program.class);
    }

    @Override
    public void addProgramSchedule(ProgramSchedule schedule) throws RemoteException {
        save(schedule);
    }

    @Override
    public void updateProgramSchedule(ProgramSchedule schedule) throws RemoteException {
        save(schedule);
    }

    @Override
    public void deleteProgramSchedule(int id) throws RemoteException {
        delete(ProgramSchedule.class, id);
    }

    @Override
    public List<ProgramSchedule> getAllProgramSchedules() throws RemoteException {
        return list(ProgramSchedule.class);
    }

    @Override
    public void addEmployee(Employee employee) throws RemoteException {
        save(employee);
    }

    @Override
    public void updateEmployee(Employee employee) throws RemoteException {
        save(employee);
    }

    @Override
    public void deleteEmployee(int id) throws RemoteException {
        delete(Employee.class, id);
    }

    @Override
    public Employee getEmployeeById(int id) throws RemoteException {
        return get(Employee.class, id);
    }

    @Override
    public List<Employee> getAllEmployees() throws RemoteException {
        return list(Employee.class);
    }

    @Override
    public void addAdvertisement(Advertisement advertisement) throws RemoteException {
        save(advertisement);
    }

    @Override
    public void updateAdvertisement(Advertisement advertisement) throws RemoteException {
        save(advertisement);
    }

    @Override
    public void deleteAdvertisement(int id) throws RemoteException {
        delete(Advertisement.class, id);
    }

    @Override
    public List<Advertisement> getAllAdvertisements() throws RemoteException {
        return list(Advertisement.class);
    }

    @Override
    public void addExpense(Expense expense) throws RemoteException {
        save(expense);
    }

    @Override
    public void updateExpense(Expense expense) throws RemoteException {
        save(expense);
    }

    @Override
    public void deleteExpense(int id) throws RemoteException {
        delete(Expense.class, id);
    }

    @Override
    public List<Expense> getAllExpenses() throws RemoteException {
        return list(Expense.class);
    }

    @Override
    public void addFinancialReport(FinancialReport report) throws RemoteException {
        save(report);
    }

    @Override
    public void deleteFinancialReport(int id) throws RemoteException {
        delete(FinancialReport.class, id);
    }

    @Override
    public List<FinancialReport> getAllFinancialReports() throws RemoteException {
        return list(FinancialReport.class);
    }

    @Override
    public void addProgramAssignment(ProgramAssignment assignment) throws RemoteException {
        save(assignment);
    }

    @Override
    public void updateProgramAssignment(ProgramAssignment assignment) throws RemoteException {
        save(assignment);
    }

    @Override
    public void deleteProgramAssignment(int id) throws RemoteException {
        delete(ProgramAssignment.class, id);
    }

    @Override
    public List<ProgramAssignment> getAllProgramAssignments() throws RemoteException {
        return list(ProgramAssignment.class);
    }

    @Override
    public void addEquipment(Equipment equipment) throws RemoteException {
        save(equipment);
    }

    @Override
    public void updateEquipment(Equipment equipment) throws RemoteException {
        save(equipment);
    }

    @Override
    public void deleteEquipment(int id) throws RemoteException {
        delete(Equipment.class, id);
    }

    @Override
    public List<Equipment> getAllEquipment() throws RemoteException {
        return list(Equipment.class);
    }
}
