/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extensions.linq.tests;

import com.extensions.linq.Q;
import com.extensions.linq.data.QMock;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author tkhalilov
 */
public class QTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public QTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void Q_IsFoundAny_ArgumentNullExceptionExpected() {
        exception.expect(IllegalArgumentException.class);

        ArrayList<QMock> mocks = null;
        new Q<QMock>(mocks);
    }

}
