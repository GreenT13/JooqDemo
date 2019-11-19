package com.apon.jooq.demo;

import com.apon.jooq.database.Context;
import com.apon.jooq.generated.tables.daos.CustomerDao;
import com.apon.jooq.generated.tables.pojos.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.util.List;

import static com.apon.jooq.generated.Tables.CUSTOMER;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String... args) {
        Context context = Context.createContextToDb();

        demoUsingDaoFunctions(context);
        demoCreatingQueries(context);
    }

    private static void demoUsingDaoFunctions(Context context) {
        CustomerDao customerDao = new CustomerDao(context.getConfiguration());

        // Fetch using customerId.
        Customer customer = customerDao.fetchOneByCustomerid(1);
        logger.info(customer);

        // Fetch using search queries.
        for (Customer Rico : customerDao.fetchByFirstname("Rico")) {
            logger.info(Rico);
        }
    }

    private static void demoCreatingQueries(Context context) {
        // Object that is used for starting all queries.
        DSLContext create = DSL.using(context.getDataSource(), SQLDialect.H2);

        // Select all externally managed customers with no first name.
        List<Customer> result = create
                .selectFrom(CUSTOMER)
                .where(CUSTOMER.ISEXTERNALLYMANAGED.eq("1"))
                .and(CUSTOMER.FIRSTNAME.isNull())
                // Fetch into specific class.
                .fetchInto(Customer.class);

        for (Customer customer : result) {
            logger.info(customer);
        }
    }
}

