package com.simulator.bank.dao;

import com.simulator.bank.config.DataSourceFactory;
import com.simulator.bank.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {

    //Get All Customers
    public List<Customer> findAll() throws SQLException {
        String sql = """
            SELECT customer_id, username, password, email, phone_number, status, dob, aadhar_number,
                   permanent_address, state, country, city, age, gender, father_name, mother_name, pin
              FROM customers
        """; 

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(mapRow(rs));
            }
            return customers;
        }
    }

    //Find by Customer ID
    public Customer findById(int id) throws SQLException {
        String sql = """
            SELECT customer_id, username, password, email, phone_number, status, dob, aadhar_number,
                   permanent_address, state, country, city, age, gender, father_name, mother_name, pin
              FROM customers
             WHERE customer_id = ?
        """; 

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        }
    }

    //Find Customer by User Name
    public Customer findByUsername(String username) throws SQLException {
        String sql = """
            SELECT customer_id, username, password, email, phone_number, status, dob, aadhar_number,
                   permanent_address, state, country, city, age, gender, father_name, mother_name, pin
              FROM customers
             WHERE username = ?
        """; 

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    //Create Customer
    public int create(Customer c) throws SQLException {
        String sql = """
            INSERT INTO customers (
                username, password, email, phone_number, status, dob, aadhar_number,
                permanent_address, state, country, city, age, gender, father_name, mother_name, pin
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getUsername());
            ps.setString(2, c.getPassword());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhoneNumber());
            ps.setString(5, c.getStatus() != null ? c.getStatus() : "ACTIVE");

            if (c.getDob() != null) ps.setDate(6, Date.valueOf(c.getDob()));
            else ps.setNull(6, Types.DATE);

            ps.setString(7, c.getAadharNumber());
            ps.setString(8, c.getPermanentAddress());
            ps.setString(9, c.getState());
            ps.setString(10, c.getCountry());
            ps.setString(11, c.getCity());

            if (c.getAge() != null) ps.setInt(12, c.getAge());
            else ps.setNull(12, Types.INTEGER);

            ps.setString(13, c.getGender());
            ps.setString(14, c.getFatherName());
            ps.setString(15, c.getMotherName());

            if (c.getPin() != null) ps.setInt(16, c.getPin());
            else ps.setNull(16, Types.INTEGER);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    c.setCustomerId(id);
                    return id;
                }
            }
            throw new SQLException("No generated key returned for Customer");
        }
    }

    //Update Customer
    public boolean update(int id, Customer c) throws SQLException {
        String sql = """
            UPDATE customers
               SET username=?, password=?, email=?, phone_number=?, status=?, dob=?, aadhar_number=?,
                   permanent_address=?, state=?, country=?, city=?, age=?, gender=?, father_name=?, mother_name=?, pin=?
             WHERE customer_id=?
        """;

        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getUsername());
            ps.setString(2, c.getPassword());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhoneNumber());
            ps.setString(5, c.getStatus());

            if (c.getDob() != null)
                ps.setDate(6, Date.valueOf(c.getDob()));
            else
                ps.setNull(6, Types.DATE);

            ps.setString(7, c.getAadharNumber());
            ps.setString(8, c.getPermanentAddress());
            ps.setString(9, c.getState());
            ps.setString(10, c.getCountry());
            ps.setString(11, c.getCity());

            if (c.getAge() != null)
                ps.setInt(12, c.getAge());
            else
                ps.setNull(12, Types.INTEGER);

            ps.setString(13, c.getGender());
            ps.setString(14, c.getFatherName());
            ps.setString(15, c.getMotherName());

            if (c.getPin() != null)
                ps.setInt(16, c.getPin());
            else
                ps.setNull(16, Types.INTEGER);

            ps.setInt(17, id);

            return ps.executeUpdate() == 1;
        }
    }

    //Delete Customer
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id=?";
        try (Connection conn = DataSourceFactory.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

   
    private Customer mapRow(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerId(rs.getInt("customer_id"));
        c.setUsername(rs.getString("username"));
        c.setPassword(rs.getString("password")); 
        c.setEmail(rs.getString("email"));
        c.setPhoneNumber(rs.getString("phone_number"));
        c.setStatus(rs.getString("status"));
        Date d = rs.getDate("dob");
        if (d != null) c.setDob(d.toLocalDate());
        c.setAadharNumber(rs.getString("aadhar_number"));
        c.setPermanentAddress(rs.getString("permanent_address"));
        c.setState(rs.getString("state"));
        c.setCountry(rs.getString("country"));
        c.setCity(rs.getString("city"));
        int age = rs.getInt("age");
        if (!rs.wasNull()) c.setAge(age);
        c.setGender(rs.getString("gender"));
        c.setFatherName(rs.getString("father_name"));
        c.setMotherName(rs.getString("mother_name"));
        int pin = rs.getInt("pin");
        if (!rs.wasNull()) c.setPin(pin);
        return c;
    }
}
