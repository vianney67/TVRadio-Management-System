package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.*;

public interface TVRadioService extends Remote {
    // User / Session
    // User login(String username, String password) throws RemoteException;
    String loginRequest(String username, String password) throws RemoteException; // Returns "OTP_SENT"

    model.Session loginConfirm(String username, String otp) throws RemoteException; // Returns Session

    void logout(String sessionId) throws RemoteException;

    void registerUser(User user) throws RemoteException;

    // Channel
    void addChannel(Channel channel) throws RemoteException;

    void updateChannel(Channel channel) throws RemoteException;

    void deleteChannel(int id) throws RemoteException;

    List<Channel> getAllChannels() throws RemoteException;

    // Program
    void addProgram(Program program) throws RemoteException;

    void updateProgram(Program program) throws RemoteException;

    void deleteProgram(int id) throws RemoteException;

    Program getProgramById(int id) throws RemoteException;

    List<Program> getAllPrograms() throws RemoteException;

    // ProgramSchedule
    void addProgramSchedule(ProgramSchedule schedule) throws RemoteException;

    void updateProgramSchedule(ProgramSchedule schedule) throws RemoteException;

    void deleteProgramSchedule(int id) throws RemoteException;

    List<ProgramSchedule> getAllProgramSchedules() throws RemoteException;

    // Employee
    void addEmployee(Employee employee) throws RemoteException;

    void updateEmployee(Employee employee) throws RemoteException;

    void deleteEmployee(int id) throws RemoteException;

    Employee getEmployeeById(int id) throws RemoteException;

    List<Employee> getAllEmployees() throws RemoteException;

    // Advertisement
    void addAdvertisement(Advertisement advertisement) throws RemoteException;

    void updateAdvertisement(Advertisement advertisement) throws RemoteException;

    void deleteAdvertisement(int id) throws RemoteException;

    List<Advertisement> getAllAdvertisements() throws RemoteException;

    // Expense
    void addExpense(Expense expense) throws RemoteException;

    void updateExpense(Expense expense) throws RemoteException;

    void deleteExpense(int id) throws RemoteException;

    List<Expense> getAllExpenses() throws RemoteException;

    // FinancialReport
    void addFinancialReport(FinancialReport report) throws RemoteException;

    void deleteFinancialReport(int id) throws RemoteException;

    List<FinancialReport> getAllFinancialReports() throws RemoteException;

    // ProgramAssignment
    void addProgramAssignment(ProgramAssignment assignment) throws RemoteException;

    void updateProgramAssignment(ProgramAssignment assignment) throws RemoteException;

    void deleteProgramAssignment(int id) throws RemoteException;

    List<ProgramAssignment> getAllProgramAssignments() throws RemoteException;

    // Equipment
    void addEquipment(Equipment equipment) throws RemoteException;

    void updateEquipment(Equipment equipment) throws RemoteException;

    void deleteEquipment(int id) throws RemoteException;

    List<Equipment> getAllEquipment() throws RemoteException;
}
