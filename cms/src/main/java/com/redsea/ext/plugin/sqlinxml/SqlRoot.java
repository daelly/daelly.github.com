package com.redsea.ext.plugin.sqlinxml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.beust.jcommander.internal.Lists;

/**
 * Created by wangrenhui on 14-1-5.
 */
@XmlRootElement
public class SqlRoot {

  @XmlElement(name = "sqlGroup")
  List<SqlGroup> sqlGroups = Lists.newArrayList();

  void addSqlRoot(SqlGroup sqlGroup) {
    sqlGroups.add(sqlGroup);
  }
}
