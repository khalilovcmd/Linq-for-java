/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.extensions.linq.data;

/**
 *
 * @author tkhalilov
 */
public class QMock {

    public int Id;
    public int Code;
    public String CountryCode;
    public String CountryIsoCode;

    public QMock(int Id, int Code, String CountryCode, String CountryIsoCode) {
        this.Id = Id;
        this.Code = Code;
        this.CountryCode = CountryCode;
        this.CountryIsoCode = CountryIsoCode;
    }
}
