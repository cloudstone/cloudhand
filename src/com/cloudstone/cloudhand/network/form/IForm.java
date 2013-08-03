/**
 * @(#)IForm.java, 2012-11-19.
 *
 */
package com.cloudstone.cloudhand.network.form;

import java.util.List;

import org.apache.http.NameValuePair;

/**
 * @author xuhongfeng
 *
 */
public interface IForm {
    public List<NameValuePair> listParams();
}
