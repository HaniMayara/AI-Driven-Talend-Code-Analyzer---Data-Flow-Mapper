package talys.projectsbusinessdomainsjoin_0_1;
import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;
@SuppressWarnings("unused")
public class ProjectsBusinessDomainsJoin implements TalendJob {
protected static void logIgnoredError(String message, Throwable cause) {
System.err.println(message);
if (cause != null) {
cause.printStackTrace();
}
}
public final Object obj = new Object();
private Object valueObject = null;
public Object getValueObject() {
return this.valueObject;
}
public void setValueObject(Object valueObject) {
this.valueObject = valueObject;
}
private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();
private final static String utf8Charset = "UTF-8";
public class PropertiesWithType extends java.util.Properties {
private static final long serialVersionUID = 1L;
private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();
public PropertiesWithType(java.util.Properties properties) {
super(properties);
}
public PropertiesWithType() {
super();
}
public void setContextType(String key, String type) {
propertyTypes.put(key, type);
}
public String getContextType(String key) {
return propertyTypes.get(key);
}
}
private java.util.Properties defaultProps = new java.util.Properties();
public class ContextProperties extends PropertiesWithType {
private static final long serialVersionUID = 1L;
public ContextProperties(java.util.Properties properties) {
super(properties);
}
public ContextProperties() {
super();
}
public void synchronizeContext() {
}
public String getStringValue(String key) {
String origin_value = this.getProperty(key);
if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
return null;
}
return origin_value;
}
}
protected ContextProperties context = new ContextProperties();
public ContextProperties getContext() {
return this.context;
}
private final String jobVersion = "0.1";
private final String jobName = "ProjectsBusinessDomainsJoin";
private final String projectName = "TALYS";
public Integer errorCode = null;
private String currentComponent = "";
private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";
private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";
public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
talendDataSources.put(dataSourceEntry.getKey(),
new routines.system.TalendDataSource(dataSourceEntry.getValue()));
}
globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
}
public void setDataSourceReferences(List serviceReferences) throws Exception {
java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();
for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
dataSources.put(entry.getKey(), entry.getValue());
talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
}
globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
}
private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));
public String getExceptionStackTrace() {
if ("failure".equals(this.getStatus())) {
errorMessagePS.flush();
return baos.toString();
}
return null;
}
private Exception exception;
public Exception getException() {
if ("failure".equals(this.getStatus())) {
return this.exception;
}
return null;
}
private class TalendException extends Exception {
private static final long serialVersionUID = 1L;
private java.util.Map<String, Object> globalMap = null;
private Exception e = null;
private String currentComponent = null;
private String virtualComponentName = null;
public void setVirtualComponentName(String virtualComponentName) {
this.virtualComponentName = virtualComponentName;
}
private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
this.currentComponent = errorComponent;
this.globalMap = globalMap;
this.e = e;
}
public Exception getException() {
return this.e;
}
public String getCurrentComponent() {
return this.currentComponent;
}
public String getExceptionCauseMessage(Exception e) {
Throwable cause = e;
String message = null;
int i = 10;
while (null != cause && 0 < i--) {
message = cause.getMessage();
if (null == message) {
cause = cause.getCause();
} else {
break;
}
}
if (null == message) {
message = e.getClass().getName();
}
return message;
}
@Override
public void printStackTrace() {
if (!(e instanceof TalendException || e instanceof TDieException)) {
if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
}
globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
}
if (!(e instanceof TDieException)) {
if (e instanceof TalendException) {
e.printStackTrace();
} else {
e.printStackTrace();
e.printStackTrace(errorMessagePS);
ProjectsBusinessDomainsJoin.this.exception = e;
}
}
if (!(e instanceof TalendException)) {
try {
for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
if (m.getName().compareTo(currentComponent + "_error") == 0) {
m.invoke(ProjectsBusinessDomainsJoin.this, new Object[] { e, currentComponent, globalMap });
break;
}
}
if (!(e instanceof TDieException)) {
}
} catch (Exception e) {
this.e.printStackTrace();
}
}
}
}
public void tDBInput_2_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputDelimited_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tAdvancedHash_row2_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_2_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public static class JointureStruct implements routines.system.IPersistableRow<JointureStruct> {
final static byte[] commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
static byte[] commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
public String name;
public String getName() {
return this.name;
}
public String description;
public String getDescription() {
return this.description;
}
public Long budget;
public Long getBudget() {
return this.budget;
}
public String name_1;
public String getName_1() {
return this.name_1;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length) {
if (length < 1024 && commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length == 0) {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[1024];
} else {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length);
strReturn = new String(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length, utf8Charset);
}
return strReturn;
}
private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length) {
if (length < 1024 && commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length == 0) {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[1024];
} else {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length);
strReturn = new String(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length, utf8Charset);
}
return strReturn;
}
private void writeString(String str, ObjectOutputStream dos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.name = readString(dis);
this.description = readString(dis);
length = dis.readByte();
if (length == -1) {
this.budget = null;
} else {
this.budget = dis.readLong();
}
this.name_1 = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.name = readString(dis);
this.description = readString(dis);
length = dis.readByte();
if (length == -1) {
this.budget = null;
} else {
this.budget = dis.readLong();
}
this.name_1 = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.name, dos);
writeString(this.description, dos);
if (this.budget == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.budget);
}
writeString(this.name_1, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.name, dos);
writeString(this.description, dos);
if (this.budget == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.budget);
}
writeString(this.name_1, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("name=" + name);
sb.append(",description=" + description);
sb.append(",budget=" + String.valueOf(budget));
sb.append(",name_1=" + name_1);
sb.append("]");
return sb.toString();
}
public int compareTo(JointureStruct other) {
int returnValue = -1;
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
final static byte[] commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
static byte[] commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
public long id;
public long getId() {
return this.id;
}
public String name;
public String getName() {
return this.name;
}
public String description;
public String getDescription() {
return this.description;
}
public Long budget;
public Long getBudget() {
return this.budget;
}
public String closing_motif;
public String getClosing_motif() {
return this.closing_motif;
}
public java.util.Date start_date;
public java.util.Date getStart_date() {
return this.start_date;
}
public java.util.Date end_date;
public java.util.Date getEnd_date() {
return this.end_date;
}
public String actual_state;
public String getActual_state() {
return this.actual_state;
}
public java.util.Date in_progress_date;
public java.util.Date getIn_progress_date() {
return this.in_progress_date;
}
public long user_id;
public long getUser_id() {
return this.user_id;
}
public long business_domain_id;
public long getBusiness_domain_id() {
return this.business_domain_id;
}
public java.util.Date closing_date;
public java.util.Date getClosing_date() {
return this.closing_date;
}
public long created_by;
public long getCreated_by() {
return this.created_by;
}
public Long started_by;
public Long getStarted_by() {
return this.started_by;
}
public Long closed_by;
public Long getClosed_by() {
return this.closed_by;
}
public Long deleted_by;
public Long getDeleted_by() {
return this.deleted_by;
}
public java.util.Date created_on;
public java.util.Date getCreated_on() {
return this.created_on;
}
public java.util.Date deleted_on;
public java.util.Date getDeleted_on() {
return this.deleted_on;
}
public Integer currency_id;
public Integer getCurrency_id() {
return this.currency_id;
}
public String project_ref;
public String getProject_ref() {
return this.project_ref;
}
public String last_modified_by;
public String getLast_modified_by() {
return this.last_modified_by;
}
public java.util.Date last_modified_date;
public java.util.Date getLast_modified_date() {
return this.last_modified_date;
}
public String project_chart;
public String getProject_chart() {
return this.project_chart;
}
public Integer project_chart_version;
public Integer getProject_chart_version() {
return this.project_chart_version;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length) {
if (length < 1024 && commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length == 0) {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[1024];
} else {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length);
strReturn = new String(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length, utf8Charset);
}
return strReturn;
}
private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length) {
if (length < 1024 && commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length == 0) {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[1024];
} else {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length);
strReturn = new String(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length, utf8Charset);
}
return strReturn;
}
private void writeString(String str, ObjectOutputStream dos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
private java.util.Date readDate(ObjectInputStream dis) throws IOException {
java.util.Date dateReturn = null;
int length = 0;
length = dis.readByte();
if (length == -1) {
dateReturn = null;
} else {
dateReturn = new Date(dis.readLong());
}
return dateReturn;
}
private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
java.util.Date dateReturn = null;
int length = 0;
length = unmarshaller.readByte();
if (length == -1) {
dateReturn = null;
} else {
dateReturn = new Date(unmarshaller.readLong());
}
return dateReturn;
}
private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
if (date1 == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(date1.getTime());
}
}
private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (date1 == null) {
marshaller.writeByte(-1);
} else {
marshaller.writeByte(0);
marshaller.writeLong(date1.getTime());
}
}
private Integer readInteger(ObjectInputStream dis) throws IOException {
Integer intReturn;
int length = 0;
length = dis.readByte();
if (length == -1) {
intReturn = null;
} else {
intReturn = dis.readInt();
}
return intReturn;
}
private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
Integer intReturn;
int length = 0;
length = dis.readByte();
if (length == -1) {
intReturn = null;
} else {
intReturn = dis.readInt();
}
return intReturn;
}
private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
if (intNum == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeInt(intNum);
}
}
private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (intNum == null) {
marshaller.writeByte(-1);
} else {
marshaller.writeByte(0);
marshaller.writeInt(intNum);
}
}
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.id = dis.readLong();
this.name = readString(dis);
this.description = readString(dis);
length = dis.readByte();
if (length == -1) {
this.budget = null;
} else {
this.budget = dis.readLong();
}
this.closing_motif = readString(dis);
this.start_date = readDate(dis);
this.end_date = readDate(dis);
this.actual_state = readString(dis);
this.in_progress_date = readDate(dis);
this.user_id = dis.readLong();
this.business_domain_id = dis.readLong();
this.closing_date = readDate(dis);
this.created_by = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.started_by = null;
} else {
this.started_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.closed_by = null;
} else {
this.closed_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.deleted_by = null;
} else {
this.deleted_by = dis.readLong();
}
this.created_on = readDate(dis);
this.deleted_on = readDate(dis);
this.currency_id = readInteger(dis);
this.project_ref = readString(dis);
this.last_modified_by = readString(dis);
this.last_modified_date = readDate(dis);
this.project_chart = readString(dis);
this.project_chart_version = readInteger(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.id = dis.readLong();
this.name = readString(dis);
this.description = readString(dis);
length = dis.readByte();
if (length == -1) {
this.budget = null;
} else {
this.budget = dis.readLong();
}
this.closing_motif = readString(dis);
this.start_date = readDate(dis);
this.end_date = readDate(dis);
this.actual_state = readString(dis);
this.in_progress_date = readDate(dis);
this.user_id = dis.readLong();
this.business_domain_id = dis.readLong();
this.closing_date = readDate(dis);
this.created_by = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.started_by = null;
} else {
this.started_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.closed_by = null;
} else {
this.closed_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.deleted_by = null;
} else {
this.deleted_by = dis.readLong();
}
this.created_on = readDate(dis);
this.deleted_on = readDate(dis);
this.currency_id = readInteger(dis);
this.project_ref = readString(dis);
this.last_modified_by = readString(dis);
this.last_modified_date = readDate(dis);
this.project_chart = readString(dis);
this.project_chart_version = readInteger(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.id);
writeString(this.name, dos);
writeString(this.description, dos);
if (this.budget == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.budget);
}
writeString(this.closing_motif, dos);
writeDate(this.start_date, dos);
writeDate(this.end_date, dos);
writeString(this.actual_state, dos);
writeDate(this.in_progress_date, dos);
dos.writeLong(this.user_id);
dos.writeLong(this.business_domain_id);
writeDate(this.closing_date, dos);
dos.writeLong(this.created_by);
if (this.started_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.started_by);
}
if (this.closed_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.closed_by);
}
if (this.deleted_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.deleted_by);
}
writeDate(this.created_on, dos);
writeDate(this.deleted_on, dos);
writeInteger(this.currency_id, dos);
writeString(this.project_ref, dos);
writeString(this.last_modified_by, dos);
writeDate(this.last_modified_date, dos);
writeString(this.project_chart, dos);
writeInteger(this.project_chart_version, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.id);
writeString(this.name, dos);
writeString(this.description, dos);
if (this.budget == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.budget);
}
writeString(this.closing_motif, dos);
writeDate(this.start_date, dos);
writeDate(this.end_date, dos);
writeString(this.actual_state, dos);
writeDate(this.in_progress_date, dos);
dos.writeLong(this.user_id);
dos.writeLong(this.business_domain_id);
writeDate(this.closing_date, dos);
dos.writeLong(this.created_by);
if (this.started_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.started_by);
}
if (this.closed_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.closed_by);
}
if (this.deleted_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.deleted_by);
}
writeDate(this.created_on, dos);
writeDate(this.deleted_on, dos);
writeInteger(this.currency_id, dos);
writeString(this.project_ref, dos);
writeString(this.last_modified_by, dos);
writeDate(this.last_modified_date, dos);
writeString(this.project_chart, dos);
writeInteger(this.project_chart_version, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("id=" + String.valueOf(id));
sb.append(",name=" + name);
sb.append(",description=" + description);
sb.append(",budget=" + String.valueOf(budget));
sb.append(",closing_motif=" + closing_motif);
sb.append(",start_date=" + String.valueOf(start_date));
sb.append(",end_date=" + String.valueOf(end_date));
sb.append(",actual_state=" + actual_state);
sb.append(",in_progress_date=" + String.valueOf(in_progress_date));
sb.append(",user_id=" + String.valueOf(user_id));
sb.append(",business_domain_id=" + String.valueOf(business_domain_id));
sb.append(",closing_date=" + String.valueOf(closing_date));
sb.append(",created_by=" + String.valueOf(created_by));
sb.append(",started_by=" + String.valueOf(started_by));
sb.append(",closed_by=" + String.valueOf(closed_by));
sb.append(",deleted_by=" + String.valueOf(deleted_by));
sb.append(",created_on=" + String.valueOf(created_on));
sb.append(",deleted_on=" + String.valueOf(deleted_on));
sb.append(",currency_id=" + String.valueOf(currency_id));
sb.append(",project_ref=" + project_ref);
sb.append(",last_modified_by=" + last_modified_by);
sb.append(",last_modified_date=" + String.valueOf(last_modified_date));
sb.append(",project_chart=" + project_chart);
sb.append(",project_chart_version=" + String.valueOf(project_chart_version));
sb.append("]");
return sb.toString();
}
public int compareTo(row1Struct other) {
int returnValue = -1;
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public static class after_tDBInput_2Struct implements routines.system.IPersistableRow<after_tDBInput_2Struct> {
final static byte[] commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
static byte[] commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long id;
public long getId() {
return this.id;
}
public String name;
public String getName() {
return this.name;
}
public String description;
public String getDescription() {
return this.description;
}
public Long budget;
public Long getBudget() {
return this.budget;
}
public String closing_motif;
public String getClosing_motif() {
return this.closing_motif;
}
public java.util.Date start_date;
public java.util.Date getStart_date() {
return this.start_date;
}
public java.util.Date end_date;
public java.util.Date getEnd_date() {
return this.end_date;
}
public String actual_state;
public String getActual_state() {
return this.actual_state;
}
public java.util.Date in_progress_date;
public java.util.Date getIn_progress_date() {
return this.in_progress_date;
}
public long user_id;
public long getUser_id() {
return this.user_id;
}
public long business_domain_id;
public long getBusiness_domain_id() {
return this.business_domain_id;
}
public java.util.Date closing_date;
public java.util.Date getClosing_date() {
return this.closing_date;
}
public long created_by;
public long getCreated_by() {
return this.created_by;
}
public Long started_by;
public Long getStarted_by() {
return this.started_by;
}
public Long closed_by;
public Long getClosed_by() {
return this.closed_by;
}
public Long deleted_by;
public Long getDeleted_by() {
return this.deleted_by;
}
public java.util.Date created_on;
public java.util.Date getCreated_on() {
return this.created_on;
}
public java.util.Date deleted_on;
public java.util.Date getDeleted_on() {
return this.deleted_on;
}
public Integer currency_id;
public Integer getCurrency_id() {
return this.currency_id;
}
public String project_ref;
public String getProject_ref() {
return this.project_ref;
}
public String last_modified_by;
public String getLast_modified_by() {
return this.last_modified_by;
}
public java.util.Date last_modified_date;
public java.util.Date getLast_modified_date() {
return this.last_modified_date;
}
public String project_chart;
public String getProject_chart() {
return this.project_chart;
}
public Integer project_chart_version;
public Integer getProject_chart_version() {
return this.project_chart_version;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.id;
this.hashCode = result;
this.hashCodeDirty = false;
}
return this.hashCode;
}
@Override
public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj == null)
return false;
if (getClass() != obj.getClass())
return false;
final after_tDBInput_2Struct other = (after_tDBInput_2Struct) obj;
if (this.id != other.id)
return false;
return true;
}
public void copyDataTo(after_tDBInput_2Struct other) {
other.id = this.id;
other.name = this.name;
other.description = this.description;
other.budget = this.budget;
other.closing_motif = this.closing_motif;
other.start_date = this.start_date;
other.end_date = this.end_date;
other.actual_state = this.actual_state;
other.in_progress_date = this.in_progress_date;
other.user_id = this.user_id;
other.business_domain_id = this.business_domain_id;
other.closing_date = this.closing_date;
other.created_by = this.created_by;
other.started_by = this.started_by;
other.closed_by = this.closed_by;
other.deleted_by = this.deleted_by;
other.created_on = this.created_on;
other.deleted_on = this.deleted_on;
other.currency_id = this.currency_id;
other.project_ref = this.project_ref;
other.last_modified_by = this.last_modified_by;
other.last_modified_date = this.last_modified_date;
other.project_chart = this.project_chart;
other.project_chart_version = this.project_chart_version;
}
public void copyKeysDataTo(after_tDBInput_2Struct other) {
other.id = this.id;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length) {
if (length < 1024 && commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length == 0) {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[1024];
} else {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length);
strReturn = new String(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length, utf8Charset);
}
return strReturn;
}
private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length) {
if (length < 1024 && commonByteArray_TALYS_ProjectsBusinessDomainsJoin.length == 0) {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[1024];
} else {
commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length);
strReturn = new String(commonByteArray_TALYS_ProjectsBusinessDomainsJoin, 0, length, utf8Charset);
}
return strReturn;
}
private void writeString(String str, ObjectOutputStream dos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
private java.util.Date readDate(ObjectInputStream dis) throws IOException {
java.util.Date dateReturn = null;
int length = 0;
length = dis.readByte();
if (length == -1) {
dateReturn = null;
} else {
dateReturn = new Date(dis.readLong());
}
return dateReturn;
}
private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
java.util.Date dateReturn = null;
int length = 0;
length = unmarshaller.readByte();
if (length == -1) {
dateReturn = null;
} else {
dateReturn = new Date(unmarshaller.readLong());
}
return dateReturn;
}
private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
if (date1 == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(date1.getTime());
}
}
private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (date1 == null) {
marshaller.writeByte(-1);
} else {
marshaller.writeByte(0);
marshaller.writeLong(date1.getTime());
}
}
private Integer readInteger(ObjectInputStream dis) throws IOException {
Integer intReturn;
int length = 0;
length = dis.readByte();
if (length == -1) {
intReturn = null;
} else {
intReturn = dis.readInt();
}
return intReturn;
}
private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
Integer intReturn;
int length = 0;
length = dis.readByte();
if (length == -1) {
intReturn = null;
} else {
intReturn = dis.readInt();
}
return intReturn;
}
private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
if (intNum == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeInt(intNum);
}
}
private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (intNum == null) {
marshaller.writeByte(-1);
} else {
marshaller.writeByte(0);
marshaller.writeInt(intNum);
}
}
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.id = dis.readLong();
this.name = readString(dis);
this.description = readString(dis);
length = dis.readByte();
if (length == -1) {
this.budget = null;
} else {
this.budget = dis.readLong();
}
this.closing_motif = readString(dis);
this.start_date = readDate(dis);
this.end_date = readDate(dis);
this.actual_state = readString(dis);
this.in_progress_date = readDate(dis);
this.user_id = dis.readLong();
this.business_domain_id = dis.readLong();
this.closing_date = readDate(dis);
this.created_by = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.started_by = null;
} else {
this.started_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.closed_by = null;
} else {
this.closed_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.deleted_by = null;
} else {
this.deleted_by = dis.readLong();
}
this.created_on = readDate(dis);
this.deleted_on = readDate(dis);
this.currency_id = readInteger(dis);
this.project_ref = readString(dis);
this.last_modified_by = readString(dis);
this.last_modified_date = readDate(dis);
this.project_chart = readString(dis);
this.project_chart_version = readInteger(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.id = dis.readLong();
this.name = readString(dis);
this.description = readString(dis);
length = dis.readByte();
if (length == -1) {
this.budget = null;
} else {
this.budget = dis.readLong();
}
this.closing_motif = readString(dis);
this.start_date = readDate(dis);
this.end_date = readDate(dis);
this.actual_state = readString(dis);
this.in_progress_date = readDate(dis);
this.user_id = dis.readLong();
this.business_domain_id = dis.readLong();
this.closing_date = readDate(dis);
this.created_by = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.started_by = null;
} else {
this.started_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.closed_by = null;
} else {
this.closed_by = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.deleted_by = null;
} else {
this.deleted_by = dis.readLong();
}
this.created_on = readDate(dis);
this.deleted_on = readDate(dis);
this.currency_id = readInteger(dis);
this.project_ref = readString(dis);
this.last_modified_by = readString(dis);
this.last_modified_date = readDate(dis);
this.project_chart = readString(dis);
this.project_chart_version = readInteger(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.id);
writeString(this.name, dos);
writeString(this.description, dos);
if (this.budget == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.budget);
}
writeString(this.closing_motif, dos);
writeDate(this.start_date, dos);
writeDate(this.end_date, dos);
writeString(this.actual_state, dos);
writeDate(this.in_progress_date, dos);
dos.writeLong(this.user_id);
dos.writeLong(this.business_domain_id);
writeDate(this.closing_date, dos);
dos.writeLong(this.created_by);
if (this.started_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.started_by);
}
if (this.closed_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.closed_by);
}
if (this.deleted_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.deleted_by);
}
writeDate(this.created_on, dos);
writeDate(this.deleted_on, dos);
writeInteger(this.currency_id, dos);
writeString(this.project_ref, dos);
writeString(this.last_modified_by, dos);
writeDate(this.last_modified_date, dos);
writeString(this.project_chart, dos);
writeInteger(this.project_chart_version, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.id);
writeString(this.name, dos);
writeString(this.description, dos);
if (this.budget == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.budget);
}
writeString(this.closing_motif, dos);
writeDate(this.start_date, dos);
writeDate(this.end_date, dos);
writeString(this.actual_state, dos);
writeDate(this.in_progress_date, dos);
dos.writeLong(this.user_id);
dos.writeLong(this.business_domain_id);
writeDate(this.closing_date, dos);
dos.writeLong(this.created_by);
if (this.started_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.started_by);
}
if (this.closed_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.closed_by);
}
if (this.deleted_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.deleted_by);
}
writeDate(this.created_on, dos);
writeDate(this.deleted_on, dos);
writeInteger(this.currency_id, dos);
writeString(this.project_ref, dos);
writeString(this.last_modified_by, dos);
writeDate(this.last_modified_date, dos);
writeString(this.project_chart, dos);
writeInteger(this.project_chart_version, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("id=" + String.valueOf(id));
sb.append(",name=" + name);
sb.append(",description=" + description);
sb.append(",budget=" + String.valueOf(budget));
sb.append(",closing_motif=" + closing_motif);
sb.append(",start_date=" + String.valueOf(start_date));
sb.append(",end_date=" + String.valueOf(end_date));
sb.append(",actual_state=" + actual_state);
sb.append(",in_progress_date=" + String.valueOf(in_progress_date));
sb.append(",user_id=" + String.valueOf(user_id));
sb.append(",business_domain_id=" + String.valueOf(business_domain_id));
sb.append(",closing_date=" + String.valueOf(closing_date));
sb.append(",created_by=" + String.valueOf(created_by));
sb.append(",started_by=" + String.valueOf(started_by));
sb.append(",closed_by=" + String.valueOf(closed_by));
sb.append(",deleted_by=" + String.valueOf(deleted_by));
sb.append(",created_on=" + String.valueOf(created_on));
sb.append(",deleted_on=" + String.valueOf(deleted_on));
sb.append(",currency_id=" + String.valueOf(currency_id));
sb.append(",project_ref=" + project_ref);
sb.append(",last_modified_by=" + last_modified_by);
sb.append(",last_modified_date=" + String.valueOf(last_modified_date));
sb.append(",project_chart=" + project_chart);
sb.append(",project_chart_version=" + String.valueOf(project_chart_version));
sb.append("]");
return sb.toString();
}
public int compareTo(after_tDBInput_2Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.id, other.id);
if (returnValue != 0) {
return returnValue;
}
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);
final boolean execStat = this.execStat;
String iterateId = "";
String currentComponent = "";
java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();
try {
boolean resumeIt = true;
if (globalResumeTicket == false && resumeEntryMethodName != null) {
String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
resumeIt = resumeEntryMethodName.equals(currentMethodName);
}
if (resumeIt || globalResumeTicket) {
globalResumeTicket = true;
tDBInput_1Process(globalMap);
row1Struct row1 = new row1Struct();
JointureStruct Jointure = new JointureStruct();
ok_Hash.put("tFileOutputDelimited_1", false);
start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
currentComponent = "tFileOutputDelimited_1";
int tos_count_tFileOutputDelimited_1 = 0;
String fileName_tFileOutputDelimited_1 = "";
fileName_tFileOutputDelimited_1 = (new java.io.File("C:/Users/pc/Desktop/out3.csv")).getAbsolutePath()
.replace("\\", "/");
String fullName_tFileOutputDelimited_1 = null;
String extension_tFileOutputDelimited_1 = null;
String directory_tFileOutputDelimited_1 = null;
if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
.lastIndexOf("/")) {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
extension_tFileOutputDelimited_1 = "";
} else {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
fileName_tFileOutputDelimited_1.lastIndexOf("."));
extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
}
directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
fileName_tFileOutputDelimited_1.lastIndexOf("/"));
} else {
if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
fileName_tFileOutputDelimited_1.lastIndexOf("."));
extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
} else {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
extension_tFileOutputDelimited_1 = "";
}
directory_tFileOutputDelimited_1 = "";
}
boolean isFileGenerated_tFileOutputDelimited_1 = true;
java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
if (filetFileOutputDelimited_1.exists()) {
throw new RuntimeException("The particular file \"" + filetFileOutputDelimited_1.getAbsoluteFile()
+ "\" already exist. If you want to overwrite the file, please uncheck the"
+ " \"Throw an error if the file already exist\" option in Advanced settings.");
}
int nb_line_tFileOutputDelimited_1 = 0;
int splitedFileNo_tFileOutputDelimited_1 = 0;
int currentRow_tFileOutputDelimited_1 = 0;
final String OUT_DELIM_tFileOutputDelimited_1 =
";"
;
final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 =
"\n"
;
if (directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
if (!dir_tFileOutputDelimited_1.exists()) {
dir_tFileOutputDelimited_1.mkdirs();
}
}
java.io.Writer outtFileOutputDelimited_1 = null;
java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
if (fileToDelete_tFileOutputDelimited_1.exists()) {
fileToDelete_tFileOutputDelimited_1.delete();
}
outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false), "ISO-8859-15"));
if (filetFileOutputDelimited_1.length() == 0) {
outtFileOutputDelimited_1.write("name");
outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write("description");
outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write("budget");
outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write("name_1");
outtFileOutputDelimited_1.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
outtFileOutputDelimited_1.flush();
}
resourceMap.put("out_tFileOutputDelimited_1", outtFileOutputDelimited_1);
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);
resourceMap.put("isFileGenerated_tFileOutputDelimited_1", isFileGenerated_tFileOutputDelimited_1);
resourceMap.put("filetFileOutputDelimited_1", filetFileOutputDelimited_1);
ok_Hash.put("tMap_1", false);
start_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tMap_1";
int tos_count_tMap_1 = 0;
org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) globalMap
.get("tHash_Lookup_row2"));
row2Struct row2HashKey = new row2Struct();
row2Struct row2Default = new row2Struct();
class Var__tMap_1__Struct {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
JointureStruct Jointure_tmp = new JointureStruct();
ok_Hash.put("tDBInput_2", false);
start_Hash.put("tDBInput_2", System.currentTimeMillis());
currentComponent = "tDBInput_2";
int tos_count_tDBInput_2 = 0;
int nb_line_tDBInput_2 = 0;
java.sql.Connection conn_tDBInput_2 = null;
String driverClass_tDBInput_2 = "org.postgresql.Driver";
java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
String dbUser_tDBInput_2 = "postgres";
final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil
.decryptPassword("enc:routine.encryption.key.v1:dXfFZvj6TBJV4CYtZY20W+ldTucaEBXP7HHsPZ2bNVI=");
String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;
String url_tDBInput_2 = "jdbc:postgresql:
conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
dbPwd_tDBInput_2);
conn_tDBInput_2.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();
String dbquery_tDBInput_2 = "SELECT * FROM dev_1.projects;";
globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
java.sql.ResultSet rs_tDBInput_2 = null;
try {
rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();
String tmpContent_tDBInput_2 = null;
while (rs_tDBInput_2.next()) {
nb_line_tDBInput_2++;
if (colQtyInRs_tDBInput_2 < 1) {
row1.id = 0;
} else {
row1.id = rs_tDBInput_2.getLong(1);
if (rs_tDBInput_2.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_2 < 2) {
row1.name = null;
} else {
row1.name = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
}
if (colQtyInRs_tDBInput_2 < 3) {
row1.description = null;
} else {
row1.description = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
}
if (colQtyInRs_tDBInput_2 < 4) {
row1.budget = null;
} else {
row1.budget = rs_tDBInput_2.getLong(4);
if (rs_tDBInput_2.wasNull()) {
row1.budget = null;
}
}
if (colQtyInRs_tDBInput_2 < 5) {
row1.closing_motif = null;
} else {
row1.closing_motif = routines.system.JDBCUtil.getString(rs_tDBInput_2, 5, false);
}
if (colQtyInRs_tDBInput_2 < 6) {
row1.start_date = null;
} else {
row1.start_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 6);
}
if (colQtyInRs_tDBInput_2 < 7) {
row1.end_date = null;
} else {
row1.end_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 7);
}
if (colQtyInRs_tDBInput_2 < 8) {
row1.actual_state = null;
} else {
row1.actual_state = routines.system.JDBCUtil.getString(rs_tDBInput_2, 8, false);
}
if (colQtyInRs_tDBInput_2 < 9) {
row1.in_progress_date = null;
} else {
row1.in_progress_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 9);
}
if (colQtyInRs_tDBInput_2 < 10) {
row1.user_id = 0;
} else {
row1.user_id = rs_tDBInput_2.getLong(10);
if (rs_tDBInput_2.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_2 < 11) {
row1.business_domain_id = 0;
} else {
row1.business_domain_id = rs_tDBInput_2.getLong(11);
if (rs_tDBInput_2.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_2 < 12) {
row1.closing_date = null;
} else {
row1.closing_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 12);
}
if (colQtyInRs_tDBInput_2 < 13) {
row1.created_by = 0;
} else {
row1.created_by = rs_tDBInput_2.getLong(13);
if (rs_tDBInput_2.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_2 < 14) {
row1.started_by = null;
} else {
row1.started_by = rs_tDBInput_2.getLong(14);
if (rs_tDBInput_2.wasNull()) {
row1.started_by = null;
}
}
if (colQtyInRs_tDBInput_2 < 15) {
row1.closed_by = null;
} else {
row1.closed_by = rs_tDBInput_2.getLong(15);
if (rs_tDBInput_2.wasNull()) {
row1.closed_by = null;
}
}
if (colQtyInRs_tDBInput_2 < 16) {
row1.deleted_by = null;
} else {
row1.deleted_by = rs_tDBInput_2.getLong(16);
if (rs_tDBInput_2.wasNull()) {
row1.deleted_by = null;
}
}
if (colQtyInRs_tDBInput_2 < 17) {
row1.created_on = null;
} else {
row1.created_on = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 17);
}
if (colQtyInRs_tDBInput_2 < 18) {
row1.deleted_on = null;
} else {
row1.deleted_on = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 18);
}
if (colQtyInRs_tDBInput_2 < 19) {
row1.currency_id = null;
} else {
row1.currency_id = rs_tDBInput_2.getInt(19);
if (rs_tDBInput_2.wasNull()) {
row1.currency_id = null;
}
}
if (colQtyInRs_tDBInput_2 < 20) {
row1.project_ref = null;
} else {
row1.project_ref = routines.system.JDBCUtil.getString(rs_tDBInput_2, 20, false);
}
if (colQtyInRs_tDBInput_2 < 21) {
row1.last_modified_by = null;
} else {
row1.last_modified_by = routines.system.JDBCUtil.getString(rs_tDBInput_2, 21, false);
}
if (colQtyInRs_tDBInput_2 < 22) {
row1.last_modified_date = null;
} else {
row1.last_modified_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 22);
}
if (colQtyInRs_tDBInput_2 < 23) {
row1.project_chart = null;
} else {
row1.project_chart = routines.system.JDBCUtil.getString(rs_tDBInput_2, 23, false);
}
if (colQtyInRs_tDBInput_2 < 24) {
row1.project_chart_version = null;
} else {
row1.project_chart_version = rs_tDBInput_2.getInt(24);
if (rs_tDBInput_2.wasNull()) {
row1.project_chart_version = null;
}
}
currentComponent = "tDBInput_2";
tos_count_tDBInput_2++;
currentComponent = "tDBInput_2";
currentComponent = "tMap_1";
boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
boolean rejectedInnerJoin_tMap_1 = false;
boolean mainRowRejected_tMap_1 = false;
boolean forceLooprow2 = false;
row2Struct row2ObjectFromLookup = null;
if (!rejectedInnerJoin_tMap_1) {
hasCasePrimitiveKeyWithNull_tMap_1 = false;
Object exprKeyValue_row2__id = row1.business_domain_id;
if (exprKeyValue_row2__id == null) {
hasCasePrimitiveKeyWithNull_tMap_1 = true;
} else {
row2HashKey.id = (long) (Long) exprKeyValue_row2__id;
}
row2HashKey.hashCodeDirty = true;
if (!hasCasePrimitiveKeyWithNull_tMap_1) {
tHash_Lookup_row2.lookup(row2HashKey);
}
}
if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) {
}
row2Struct row2 = null;
row2Struct fromLookup_row2 = null;
row2 = row2Default;
if (tHash_Lookup_row2 != null && tHash_Lookup_row2.hasNext()) {
fromLookup_row2 = tHash_Lookup_row2.next();
}
if (fromLookup_row2 != null) {
row2 = fromLookup_row2;
}
{
Var__tMap_1__Struct Var = Var__tMap_1;
Jointure = null;
Jointure_tmp.name = row1.name;
Jointure_tmp.description = row1.description;
Jointure_tmp.budget = row1.budget;
Jointure_tmp.name_1 = row2.name;
Jointure = Jointure_tmp;
}
rejectedInnerJoin_tMap_1 = false;
tos_count_tMap_1++;
currentComponent = "tMap_1";
if (Jointure != null) {
currentComponent = "tFileOutputDelimited_1";
StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
if (Jointure.name != null) {
sb_tFileOutputDelimited_1.append(Jointure.name);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (Jointure.description != null) {
sb_tFileOutputDelimited_1.append(Jointure.description);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (Jointure.budget != null) {
sb_tFileOutputDelimited_1.append(Jointure.budget);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (Jointure.name_1 != null) {
sb_tFileOutputDelimited_1.append(Jointure.name_1);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
nb_line_tFileOutputDelimited_1++;
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());
tos_count_tFileOutputDelimited_1++;
currentComponent = "tFileOutputDelimited_1";
currentComponent = "tFileOutputDelimited_1";
}
currentComponent = "tMap_1";
currentComponent = "tDBInput_2";
currentComponent = "tDBInput_2";
}
} finally {
if (rs_tDBInput_2 != null) {
rs_tDBInput_2.close();
}
if (stmt_tDBInput_2 != null) {
stmt_tDBInput_2.close();
}
if (conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {
conn_tDBInput_2.commit();
conn_tDBInput_2.close();
if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
&& routines.system.BundleUtils.inOSGi()) {
Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
.getMethod("checkedShutdown").invoke(null, (Object[]) null);
}
}
}
globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);
ok_Hash.put("tDBInput_2", true);
end_Hash.put("tDBInput_2", System.currentTimeMillis());
currentComponent = "tMap_1";
if (tHash_Lookup_row2 != null) {
tHash_Lookup_row2.endGet();
}
globalMap.remove("tHash_Lookup_row2");
ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tFileOutputDelimited_1";
if (outtFileOutputDelimited_1 != null) {
outtFileOutputDelimited_1.flush();
outtFileOutputDelimited_1.close();
}
globalMap.put("tFileOutputDelimited_1_NB_LINE", nb_line_tFileOutputDelimited_1);
globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
if (isFileGenerated_tFileOutputDelimited_1 && nb_line_tFileOutputDelimited_1 == 0) {
filetFileOutputDelimited_1.delete();
}
resourceMap.put("finish_tFileOutputDelimited_1", true);
ok_Hash.put("tFileOutputDelimited_1", true);
end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
throw error;
} finally {
globalMap.remove("tHash_Lookup_row2");
try {
currentComponent = "tDBInput_2";
currentComponent = "tMap_1";
currentComponent = "tFileOutputDelimited_1";
if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {
java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer) resourceMap
.get("out_tFileOutputDelimited_1");
if (outtFileOutputDelimited_1 != null) {
outtFileOutputDelimited_1.flush();
outtFileOutputDelimited_1.close();
}
if (Boolean.valueOf(String.valueOf(resourceMap.get("isFileGenerated_tFileOutputDelimited_1")))
&& Integer
.valueOf(String.valueOf(resourceMap.get("nb_line_tFileOutputDelimited_1"))) == 0) {
((java.io.File) resourceMap.get("filetFileOutputDelimited_1")).delete();
}
}
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
}
public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
final static byte[] commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
static byte[] commonByteArray_TALYS_ProjectsBusinessDomainsJoin = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long id;
public long getId() {
return this.id;
}
public String name;
public String getName() {
return this.name;
}
public String description;
public String getDescription() {
return this.description;
}
public long created_by;
public long getCreated_by() {
return this.created_by;
}
public Long deleted_by;
public Long getDeleted_by() {
return this.deleted_by;
}
public java.util.Date created_on;
public java.util.Date getCreated_on() {
return this.created_on;
}
public java.util.Date deleted_on;
public java.util.Date getDeleted_on() {
return this.deleted_on;
}
public String last_modified_by;
public String getLast_modified_by() {
return this.last_modified_by;
}
public java.util.Date last_modified_date;
public java.util.Date getLast_modified_date() {
return this.last_modified_date;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.id;
this.hashCode = result;
this.hashCodeDirty = false;
}
return this.hashCode;
}
@Override
public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj == null)
return false;
if (getClass() != obj.getClass())
return false;
final row2Struct other = (row2Struct) obj;
if (this.id != other.id)
return false;
return true;
}
public void copyDataTo(row2Struct other) {
other.id = this.id;
other.name = this.name;
other.description = this.description;
other.created_by = this.created_by;
other.deleted_by = this.deleted_by;
other.created_on = this.created_on;
other.deleted_on = this.deleted_on;
other.last_modified_by = this.last_modified_by;
other.last_modified_date = this.last_modified_date;
}
public void copyKeysDataTo(row2Struct other) {
other.id = this.id;
}
private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
byte[] byteArray = new byte[length];
dis.read(byteArray);
strReturn = new String(byteArray, utf8Charset);
}
return strReturn;
}
private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
byte[] byteArray = new byte[length];
unmarshaller.read(byteArray);
strReturn = new String(byteArray, utf8Charset);
}
return strReturn;
}
private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
private java.util.Date readDate(DataInputStream dis, ObjectInputStream ois) throws IOException {
java.util.Date dateReturn = null;
int length = 0;
length = dis.readByte();
if (length == -1) {
dateReturn = null;
} else {
dateReturn = new Date(dis.readLong());
}
return dateReturn;
}
private java.util.Date readDate(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
throws IOException {
java.util.Date dateReturn = null;
int length = 0;
length = unmarshaller.readByte();
if (length == -1) {
dateReturn = null;
} else {
dateReturn = new Date(unmarshaller.readLong());
}
return dateReturn;
}
private void writeDate(java.util.Date date1, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
if (date1 == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(date1.getTime());
}
}
private void writeDate(java.util.Date date1, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
throws IOException {
if (date1 == null) {
marshaller.writeByte(-1);
} else {
marshaller.writeByte(0);
marshaller.writeLong(date1.getTime());
}
}
public void readKeysData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_ProjectsBusinessDomainsJoin) {
try {
int length = 0;
this.id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeKeysData(ObjectOutputStream dos) {
try {
dos.writeLong(this.id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
try {
int length = 0;
this.name = readString(dis, ois);
this.description = readString(dis, ois);
this.created_by = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.deleted_by = null;
} else {
this.deleted_by = dis.readLong();
}
this.created_on = readDate(dis, ois);
this.deleted_on = readDate(dis, ois);
this.last_modified_by = readString(dis, ois);
this.last_modified_date = readDate(dis, ois);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
try {
int length = 0;
this.name = readString(dis, objectIn);
this.description = readString(dis, objectIn);
this.created_by = objectIn.readLong();
length = objectIn.readByte();
if (length == -1) {
this.deleted_by = null;
} else {
this.deleted_by = objectIn.readLong();
}
this.created_on = readDate(dis, objectIn);
this.deleted_on = readDate(dis, objectIn);
this.last_modified_by = readString(dis, objectIn);
this.last_modified_date = readDate(dis, objectIn);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
try {
writeString(this.name, dos, oos);
writeString(this.description, dos, oos);
dos.writeLong(this.created_by);
if (this.deleted_by == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.deleted_by);
}
writeDate(this.created_on, dos, oos);
writeDate(this.deleted_on, dos, oos);
writeString(this.last_modified_by, dos, oos);
writeDate(this.last_modified_date, dos, oos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
try {
writeString(this.name, dos, objectOut);
writeString(this.description, dos, objectOut);
objectOut.writeLong(this.created_by);
if (this.deleted_by == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeLong(this.deleted_by);
}
writeDate(this.created_on, dos, objectOut);
writeDate(this.deleted_on, dos, objectOut);
writeString(this.last_modified_by, dos, objectOut);
writeDate(this.last_modified_date, dos, objectOut);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public boolean supportMarshaller() {
return true;
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("id=" + String.valueOf(id));
sb.append(",name=" + name);
sb.append(",description=" + description);
sb.append(",created_by=" + String.valueOf(created_by));
sb.append(",deleted_by=" + String.valueOf(deleted_by));
sb.append(",created_on=" + String.valueOf(created_on));
sb.append(",deleted_on=" + String.valueOf(deleted_on));
sb.append(",last_modified_by=" + last_modified_by);
sb.append(",last_modified_date=" + String.valueOf(last_modified_date));
sb.append("]");
return sb.toString();
}
public int compareTo(row2Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.id, other.id);
if (returnValue != 0) {
return returnValue;
}
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);
final boolean execStat = this.execStat;
String iterateId = "";
String currentComponent = "";
java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();
try {
boolean resumeIt = true;
if (globalResumeTicket == false && resumeEntryMethodName != null) {
String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
resumeIt = resumeEntryMethodName.equals(currentMethodName);
}
if (resumeIt || globalResumeTicket) {
globalResumeTicket = true;
row2Struct row2 = new row2Struct();
ok_Hash.put("tAdvancedHash_row2", false);
start_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());
currentComponent = "tAdvancedHash_row2";
int tos_count_tAdvancedHash_row2 = 0;
org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
.<row2Struct>getLookup(matchingModeEnum_row2);
globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);
ok_Hash.put("tDBInput_1", false);
start_Hash.put("tDBInput_1", System.currentTimeMillis());
currentComponent = "tDBInput_1";
int tos_count_tDBInput_1 = 0;
int nb_line_tDBInput_1 = 0;
java.sql.Connection conn_tDBInput_1 = null;
String driverClass_tDBInput_1 = "org.postgresql.Driver";
java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
String dbUser_tDBInput_1 = "postgres";
final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil
.decryptPassword("enc:routine.encryption.key.v1:fWNVCXENE+/hQgTSVkN1dB5zQHFfO60sRu7YlOQwpLQ=");
String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;
String url_tDBInput_1 = "jdbc:postgresql:
conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
dbPwd_tDBInput_1);
conn_tDBInput_1.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();
String dbquery_tDBInput_1 = "SELECT * FROM dev_1.business_domains;";
globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
java.sql.ResultSet rs_tDBInput_1 = null;
try {
rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();
String tmpContent_tDBInput_1 = null;
while (rs_tDBInput_1.next()) {
nb_line_tDBInput_1++;
if (colQtyInRs_tDBInput_1 < 1) {
row2.id = 0;
} else {
row2.id = rs_tDBInput_1.getLong(1);
if (rs_tDBInput_1.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_1 < 2) {
row2.name = null;
} else {
row2.name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
}
if (colQtyInRs_tDBInput_1 < 3) {
row2.description = null;
} else {
row2.description = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
}
if (colQtyInRs_tDBInput_1 < 4) {
row2.created_by = 0;
} else {
row2.created_by = rs_tDBInput_1.getLong(4);
if (rs_tDBInput_1.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_1 < 5) {
row2.deleted_by = null;
} else {
row2.deleted_by = rs_tDBInput_1.getLong(5);
if (rs_tDBInput_1.wasNull()) {
row2.deleted_by = null;
}
}
if (colQtyInRs_tDBInput_1 < 6) {
row2.created_on = null;
} else {
row2.created_on = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 6);
}
if (colQtyInRs_tDBInput_1 < 7) {
row2.deleted_on = null;
} else {
row2.deleted_on = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 7);
}
if (colQtyInRs_tDBInput_1 < 8) {
row2.last_modified_by = null;
} else {
row2.last_modified_by = routines.system.JDBCUtil.getString(rs_tDBInput_1, 8, false);
}
if (colQtyInRs_tDBInput_1 < 9) {
row2.last_modified_date = null;
} else {
row2.last_modified_date = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 9);
}
currentComponent = "tDBInput_1";
tos_count_tDBInput_1++;
currentComponent = "tDBInput_1";
currentComponent = "tAdvancedHash_row2";
row2Struct row2_HashRow = new row2Struct();
row2_HashRow.id = row2.id;
row2_HashRow.name = row2.name;
row2_HashRow.description = row2.description;
row2_HashRow.created_by = row2.created_by;
row2_HashRow.deleted_by = row2.deleted_by;
row2_HashRow.created_on = row2.created_on;
row2_HashRow.deleted_on = row2.deleted_on;
row2_HashRow.last_modified_by = row2.last_modified_by;
row2_HashRow.last_modified_date = row2.last_modified_date;
tHash_Lookup_row2.put(row2_HashRow);
tos_count_tAdvancedHash_row2++;
currentComponent = "tAdvancedHash_row2";
currentComponent = "tAdvancedHash_row2";
currentComponent = "tDBInput_1";
currentComponent = "tDBInput_1";
}
} finally {
if (rs_tDBInput_1 != null) {
rs_tDBInput_1.close();
}
if (stmt_tDBInput_1 != null) {
stmt_tDBInput_1.close();
}
if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {
conn_tDBInput_1.commit();
conn_tDBInput_1.close();
if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
&& routines.system.BundleUtils.inOSGi()) {
Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
.getMethod("checkedShutdown").invoke(null, (Object[]) null);
}
}
}
globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);
ok_Hash.put("tDBInput_1", true);
end_Hash.put("tDBInput_1", System.currentTimeMillis());
currentComponent = "tAdvancedHash_row2";
tHash_Lookup_row2.endPut();
ok_Hash.put("tAdvancedHash_row2", true);
end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
throw error;
} finally {
try {
currentComponent = "tDBInput_1";
currentComponent = "tAdvancedHash_row2";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
}
public String resuming_logs_dir_path = null;
public String resuming_checkpoint_path = null;
public String parent_part_launcher = null;
private String resumeEntryMethodName = null;
private boolean globalResumeTicket = false;
public boolean watch = false;
public Integer portStats = null;
public int portTraces = 4334;
public String clientHost;
public String defaultClientHost = "localhost";
public String contextStr = "Default";
public boolean isDefaultContext = true;
public String pid = "0";
public String rootPid = null;
public String fatherPid = null;
public String fatherNode = null;
public long startTime = 0;
public boolean isChildJob = false;
public String log4jLevel = "";
private boolean enableLogStash;
private boolean execStat = true;
private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
protected java.util.Map<String, String> initialValue() {
java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
threadRunResultMap.put("errorCode", null);
threadRunResultMap.put("status", "");
return threadRunResultMap;
};
};
protected PropertiesWithType context_param = new PropertiesWithType();
public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();
public String status = "";
public static void main(String[] args) {
final ProjectsBusinessDomainsJoin ProjectsBusinessDomainsJoinClass = new ProjectsBusinessDomainsJoin();
int exitCode = ProjectsBusinessDomainsJoinClass.runJobInTOS(args);
System.exit(exitCode);
}
public String[][] runJob(String[] args) {
int exitCode = runJobInTOS(args);
String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };
return bufferValue;
}
public boolean hastBufferOutputComponent() {
boolean hastBufferOutput = false;
return hastBufferOutput;
}
public int runJobInTOS(String[] args) {
status = "";
String lastStr = "";
for (String arg : args) {
if (arg.equalsIgnoreCase("--context_param")) {
lastStr = arg;
} else if (lastStr.equals("")) {
evalParam(arg);
} else {
evalParam(lastStr + " " + arg);
lastStr = "";
}
}
enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));
if (clientHost == null) {
clientHost = defaultClientHost;
}
if (pid == null || "0".equals(pid)) {
pid = TalendString.getAsciiRandomString(6);
}
if (rootPid == null) {
rootPid = pid;
}
if (fatherPid == null) {
fatherPid = pid;
} else {
isChildJob = true;
}
boolean inOSGi = routines.system.BundleUtils.inOSGi();
if (inOSGi) {
java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);
if (jobProperties != null && jobProperties.get("context") != null) {
contextStr = (String) jobProperties.get("context");
}
}
try {
java.io.InputStream inContext = ProjectsBusinessDomainsJoin.class.getClassLoader().getResourceAsStream(
"talys/projectsbusinessdomainsjoin_0_1/contexts/" + contextStr + ".properties");
if (inContext == null) {
inContext = ProjectsBusinessDomainsJoin.class.getClassLoader()
.getResourceAsStream("config/contexts/" + contextStr + ".properties");
}
if (inContext != null) {
try {
if (context != null && context.isEmpty()) {
defaultProps.load(inContext);
context = new ContextProperties(defaultProps);
}
} finally {
inContext.close();
}
} else if (!isDefaultContext) {
System.err.println("Could not find the context " + contextStr);
}
if (!context_param.isEmpty()) {
context.putAll(context_param);
for (Object key : context_param.keySet()) {
String context_key = key.toString();
String context_type = context_param.getContextType(context_key);
context.setContextType(context_key, context_type);
}
}
class ContextProcessing {
private void processContext_0() {
}
public void processAllContext() {
processContext_0();
}
}
new ContextProcessing().processAllContext();
} catch (java.io.IOException ie) {
System.err.println("Could not load context " + contextStr);
ie.printStackTrace();
}
if (parentContextMap != null && !parentContextMap.isEmpty()) {
}
resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);
List<String> parametersToEncrypt = new java.util.ArrayList<String>();
resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));
java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
globalMap.put("concurrentHashMap", concurrentHashMap);
long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
long endUsedMemory = 0;
long end = 0;
startTime = System.currentTimeMillis();
this.globalResumeTicket = true;
this.globalResumeTicket = false;
try {
errorCode = null;
tDBInput_2Process(globalMap);
if (!"failure".equals(status)) {
status = "end";
}
} catch (TalendException e_tDBInput_2) {
globalMap.put("tDBInput_2_SUBPROCESS_STATE", -1);
e_tDBInput_2.printStackTrace();
}
this.globalResumeTicket = true;
end = System.currentTimeMillis();
if (watch) {
System.out.println((end - startTime) + " milliseconds");
}
endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
if (false) {
System.out.println((endUsedMemory - startUsedMemory)
+ " bytes memory increase when running : ProjectsBusinessDomainsJoin");
}
int returnCode = 0;
if (errorCode == null) {
returnCode = status != null && status.equals("failure") ? 1 : 0;
} else {
returnCode = errorCode.intValue();
}
resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
"" + returnCode, "", "", "");
return returnCode;
}
public void destroy() {
}
private java.util.Map<String, Object> getSharedConnections4REST() {
java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
return connections;
}
private void evalParam(String arg) {
if (arg.startsWith("--resuming_logs_dir_path")) {
resuming_logs_dir_path = arg.substring(25);
} else if (arg.startsWith("--resuming_checkpoint_path")) {
resuming_checkpoint_path = arg.substring(27);
} else if (arg.startsWith("--parent_part_launcher")) {
parent_part_launcher = arg.substring(23);
} else if (arg.startsWith("--watch")) {
watch = true;
} else if (arg.startsWith("--stat_port=")) {
String portStatsStr = arg.substring(12);
if (portStatsStr != null && !portStatsStr.equals("null")) {
portStats = Integer.parseInt(portStatsStr);
}
} else if (arg.startsWith("--trace_port=")) {
portTraces = Integer.parseInt(arg.substring(13));
} else if (arg.startsWith("--client_host=")) {
clientHost = arg.substring(14);
} else if (arg.startsWith("--context=")) {
contextStr = arg.substring(10);
isDefaultContext = false;
} else if (arg.startsWith("--father_pid=")) {
fatherPid = arg.substring(13);
} else if (arg.startsWith("--root_pid=")) {
rootPid = arg.substring(11);
} else if (arg.startsWith("--father_node=")) {
fatherNode = arg.substring(14);
} else if (arg.startsWith("--pid=")) {
pid = arg.substring(6);
} else if (arg.startsWith("--context_type")) {
String keyValue = arg.substring(15);
int index = -1;
if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
if (fatherPid == null) {
context_param.setContextType(keyValue.substring(0, index),
replaceEscapeChars(keyValue.substring(index + 1)));
} else {
context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
}
}
} else if (arg.startsWith("--context_param")) {
String keyValue = arg.substring(16);
int index = -1;
if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
if (fatherPid == null) {
context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
} else {
context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
}
}
} else if (arg.startsWith("--log4jLevel=")) {
log4jLevel = arg.substring(13);
} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {
final int equal = arg.indexOf('=');
final String key = arg.substring("--".length(), equal);
System.setProperty(key, arg.substring(equal + 1));
}
}
private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";
private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };
private String replaceEscapeChars(String keyValue) {
if (keyValue == null || ("").equals(keyValue.trim())) {
return keyValue;
}
StringBuilder result = new StringBuilder();
int currIndex = 0;
while (currIndex < keyValue.length()) {
int index = -1;
for (String[] strArray : escapeChars) {
index = keyValue.indexOf(strArray[0], currIndex);
if (index >= 0) {
result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
strArray[1]));
currIndex = index + strArray[0].length();
break;
}
}
if (index < 0) {
result.append(keyValue.substring(currIndex));
currIndex = currIndex + keyValue.length();
}
}
return result.toString();
}
public Integer getErrorCode() {
return errorCode;
}
public String getStatus() {
return status;
}
ResumeUtil resumeUtil = null;
}