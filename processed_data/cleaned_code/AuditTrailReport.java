package talys.audittrailreport_0_1;
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
public class AuditTrailReport implements TalendJob {
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
private final String jobName = "AuditTrailReport";
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
AuditTrailReport.this.exception = e;
}
}
if (!(e instanceof TalendException)) {
try {
for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
if (m.getName().compareTo(currentComponent + "_error") == 0) {
m.invoke(AuditTrailReport.this, new Object[] { e, currentComponent, globalMap });
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
public void tDBInput_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tFilterRow_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputExcel_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_2_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputExcel_2_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public void tDBInput_2_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
final static byte[] commonByteArrayLock_TALYS_AuditTrailReport = new byte[0];
static byte[] commonByteArray_TALYS_AuditTrailReport = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long rev;
public long getRev() {
return this.rev;
}
public Short revtype;
public Short getRevtype() {
return this.revtype;
}
public Long id;
public Long getId() {
return this.id;
}
public String username;
public String getUsername() {
return this.username;
}
public String email;
public String getEmail() {
return this.email;
}
public String avatar_url;
public String getAvatar_url() {
return this.avatar_url;
}
public Boolean enabled;
public Boolean getEnabled() {
return this.enabled;
}
public Integer is_admin;
public Integer getIs_admin() {
return this.is_admin;
}
public java.util.Date last_modified_date;
public java.util.Date getLast_modified_date() {
return this.last_modified_date;
}
public String last_modified_by;
public String getLast_modified_by() {
return this.last_modified_by;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.rev;
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
final row3Struct other = (row3Struct) obj;
if (this.rev != other.rev)
return false;
return true;
}
public void copyDataTo(row3Struct other) {
other.rev = this.rev;
other.revtype = this.revtype;
other.id = this.id;
other.username = this.username;
other.email = this.email;
other.avatar_url = this.avatar_url;
other.enabled = this.enabled;
other.is_admin = this.is_admin;
other.last_modified_date = this.last_modified_date;
other.last_modified_by = this.last_modified_by;
}
public void copyKeysDataTo(row3Struct other) {
other.rev = this.rev;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_AuditTrailReport.length) {
if (length < 1024 && commonByteArray_TALYS_AuditTrailReport.length == 0) {
commonByteArray_TALYS_AuditTrailReport = new byte[1024];
} else {
commonByteArray_TALYS_AuditTrailReport = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_AuditTrailReport, 0, length);
strReturn = new String(commonByteArray_TALYS_AuditTrailReport, 0, length, utf8Charset);
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
if (length > commonByteArray_TALYS_AuditTrailReport.length) {
if (length < 1024 && commonByteArray_TALYS_AuditTrailReport.length == 0) {
commonByteArray_TALYS_AuditTrailReport = new byte[1024];
} else {
commonByteArray_TALYS_AuditTrailReport = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_AuditTrailReport, 0, length);
strReturn = new String(commonByteArray_TALYS_AuditTrailReport, 0, length, utf8Charset);
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
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_AuditTrailReport) {
try {
int length = 0;
this.rev = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.revtype = null;
} else {
this.revtype = dis.readShort();
}
length = dis.readByte();
if (length == -1) {
this.id = null;
} else {
this.id = dis.readLong();
}
this.username = readString(dis);
this.email = readString(dis);
this.avatar_url = readString(dis);
length = dis.readByte();
if (length == -1) {
this.enabled = null;
} else {
this.enabled = dis.readBoolean();
}
this.is_admin = readInteger(dis);
this.last_modified_date = readDate(dis);
this.last_modified_by = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_AuditTrailReport) {
try {
int length = 0;
this.rev = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.revtype = null;
} else {
this.revtype = dis.readShort();
}
length = dis.readByte();
if (length == -1) {
this.id = null;
} else {
this.id = dis.readLong();
}
this.username = readString(dis);
this.email = readString(dis);
this.avatar_url = readString(dis);
length = dis.readByte();
if (length == -1) {
this.enabled = null;
} else {
this.enabled = dis.readBoolean();
}
this.is_admin = readInteger(dis);
this.last_modified_date = readDate(dis);
this.last_modified_by = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.rev);
if (this.revtype == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeShort(this.revtype);
}
if (this.id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.id);
}
writeString(this.username, dos);
writeString(this.email, dos);
writeString(this.avatar_url, dos);
if (this.enabled == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.enabled);
}
writeInteger(this.is_admin, dos);
writeDate(this.last_modified_date, dos);
writeString(this.last_modified_by, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.rev);
if (this.revtype == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeShort(this.revtype);
}
if (this.id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.id);
}
writeString(this.username, dos);
writeString(this.email, dos);
writeString(this.avatar_url, dos);
if (this.enabled == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.enabled);
}
writeInteger(this.is_admin, dos);
writeDate(this.last_modified_date, dos);
writeString(this.last_modified_by, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("rev=" + String.valueOf(rev));
sb.append(",revtype=" + String.valueOf(revtype));
sb.append(",id=" + String.valueOf(id));
sb.append(",username=" + username);
sb.append(",email=" + email);
sb.append(",avatar_url=" + avatar_url);
sb.append(",enabled=" + String.valueOf(enabled));
sb.append(",is_admin=" + String.valueOf(is_admin));
sb.append(",last_modified_date=" + String.valueOf(last_modified_date));
sb.append(",last_modified_by=" + last_modified_by);
sb.append("]");
return sb.toString();
}
public int compareTo(row3Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.rev, other.rev);
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
public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
final static byte[] commonByteArrayLock_TALYS_AuditTrailReport = new byte[0];
static byte[] commonByteArray_TALYS_AuditTrailReport = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long rev;
public long getRev() {
return this.rev;
}
public Short revtype;
public Short getRevtype() {
return this.revtype;
}
public Long id;
public Long getId() {
return this.id;
}
public String username;
public String getUsername() {
return this.username;
}
public String email;
public String getEmail() {
return this.email;
}
public String avatar_url;
public String getAvatar_url() {
return this.avatar_url;
}
public Boolean enabled;
public Boolean getEnabled() {
return this.enabled;
}
public Integer is_admin;
public Integer getIs_admin() {
return this.is_admin;
}
public java.util.Date last_modified_date;
public java.util.Date getLast_modified_date() {
return this.last_modified_date;
}
public String last_modified_by;
public String getLast_modified_by() {
return this.last_modified_by;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.rev;
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
final row1Struct other = (row1Struct) obj;
if (this.rev != other.rev)
return false;
return true;
}
public void copyDataTo(row1Struct other) {
other.rev = this.rev;
other.revtype = this.revtype;
other.id = this.id;
other.username = this.username;
other.email = this.email;
other.avatar_url = this.avatar_url;
other.enabled = this.enabled;
other.is_admin = this.is_admin;
other.last_modified_date = this.last_modified_date;
other.last_modified_by = this.last_modified_by;
}
public void copyKeysDataTo(row1Struct other) {
other.rev = this.rev;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_AuditTrailReport.length) {
if (length < 1024 && commonByteArray_TALYS_AuditTrailReport.length == 0) {
commonByteArray_TALYS_AuditTrailReport = new byte[1024];
} else {
commonByteArray_TALYS_AuditTrailReport = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_AuditTrailReport, 0, length);
strReturn = new String(commonByteArray_TALYS_AuditTrailReport, 0, length, utf8Charset);
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
if (length > commonByteArray_TALYS_AuditTrailReport.length) {
if (length < 1024 && commonByteArray_TALYS_AuditTrailReport.length == 0) {
commonByteArray_TALYS_AuditTrailReport = new byte[1024];
} else {
commonByteArray_TALYS_AuditTrailReport = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_AuditTrailReport, 0, length);
strReturn = new String(commonByteArray_TALYS_AuditTrailReport, 0, length, utf8Charset);
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
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_AuditTrailReport) {
try {
int length = 0;
this.rev = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.revtype = null;
} else {
this.revtype = dis.readShort();
}
length = dis.readByte();
if (length == -1) {
this.id = null;
} else {
this.id = dis.readLong();
}
this.username = readString(dis);
this.email = readString(dis);
this.avatar_url = readString(dis);
length = dis.readByte();
if (length == -1) {
this.enabled = null;
} else {
this.enabled = dis.readBoolean();
}
this.is_admin = readInteger(dis);
this.last_modified_date = readDate(dis);
this.last_modified_by = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_AuditTrailReport) {
try {
int length = 0;
this.rev = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.revtype = null;
} else {
this.revtype = dis.readShort();
}
length = dis.readByte();
if (length == -1) {
this.id = null;
} else {
this.id = dis.readLong();
}
this.username = readString(dis);
this.email = readString(dis);
this.avatar_url = readString(dis);
length = dis.readByte();
if (length == -1) {
this.enabled = null;
} else {
this.enabled = dis.readBoolean();
}
this.is_admin = readInteger(dis);
this.last_modified_date = readDate(dis);
this.last_modified_by = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.rev);
if (this.revtype == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeShort(this.revtype);
}
if (this.id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.id);
}
writeString(this.username, dos);
writeString(this.email, dos);
writeString(this.avatar_url, dos);
if (this.enabled == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.enabled);
}
writeInteger(this.is_admin, dos);
writeDate(this.last_modified_date, dos);
writeString(this.last_modified_by, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.rev);
if (this.revtype == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeShort(this.revtype);
}
if (this.id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.id);
}
writeString(this.username, dos);
writeString(this.email, dos);
writeString(this.avatar_url, dos);
if (this.enabled == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.enabled);
}
writeInteger(this.is_admin, dos);
writeDate(this.last_modified_date, dos);
writeString(this.last_modified_by, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("rev=" + String.valueOf(rev));
sb.append(",revtype=" + String.valueOf(revtype));
sb.append(",id=" + String.valueOf(id));
sb.append(",username=" + username);
sb.append(",email=" + email);
sb.append(",avatar_url=" + avatar_url);
sb.append(",enabled=" + String.valueOf(enabled));
sb.append(",is_admin=" + String.valueOf(is_admin));
sb.append(",last_modified_date=" + String.valueOf(last_modified_date));
sb.append(",last_modified_by=" + last_modified_by);
sb.append("]");
return sb.toString();
}
public int compareTo(row1Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.rev, other.rev);
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
row1Struct row1 = new row1Struct();
row3Struct row3 = new row3Struct();
ok_Hash.put("tFileOutputExcel_1", false);
start_Hash.put("tFileOutputExcel_1", System.currentTimeMillis());
currentComponent = "tFileOutputExcel_1";
int tos_count_tFileOutputExcel_1 = 0;
int columnIndex_tFileOutputExcel_1 = 0;
boolean headerIsInserted_tFileOutputExcel_1 = false;
int nb_line_tFileOutputExcel_1 = 0;
String fileName_tFileOutputExcel_1 = "C:/Users/pc/Desktop/out.xls";
java.io.File file_tFileOutputExcel_1 = new java.io.File(fileName_tFileOutputExcel_1);
boolean isFileGenerated_tFileOutputExcel_1 = true;
java.io.File parentFile_tFileOutputExcel_1 = file_tFileOutputExcel_1.getParentFile();
if (parentFile_tFileOutputExcel_1 != null && !parentFile_tFileOutputExcel_1.exists()) {
parentFile_tFileOutputExcel_1.mkdirs();
}
jxl.write.WritableWorkbook writeableWorkbook_tFileOutputExcel_1 = null;
jxl.write.WritableSheet writableSheet_tFileOutputExcel_1 = null;
jxl.WorkbookSettings workbookSettings_tFileOutputExcel_1 = new jxl.WorkbookSettings();
workbookSettings_tFileOutputExcel_1.setEncoding("ISO-8859-15");
writeableWorkbook_tFileOutputExcel_1 = new jxl.write.biff.WritableWorkbookImpl(
new java.io.BufferedOutputStream(new java.io.FileOutputStream(fileName_tFileOutputExcel_1)),
true, workbookSettings_tFileOutputExcel_1);
writableSheet_tFileOutputExcel_1 = writeableWorkbook_tFileOutputExcel_1.getSheet("Sheet1");
if (writableSheet_tFileOutputExcel_1 == null) {
writableSheet_tFileOutputExcel_1 = writeableWorkbook_tFileOutputExcel_1.createSheet("Sheet1",
writeableWorkbook_tFileOutputExcel_1.getNumberOfSheets());
}
int startRowNum_tFileOutputExcel_1 = writableSheet_tFileOutputExcel_1.getRows();
int[] fitWidth_tFileOutputExcel_1 = new int[10];
for (int i_tFileOutputExcel_1 = 0; i_tFileOutputExcel_1 < 10; i_tFileOutputExcel_1++) {
int fitCellViewSize_tFileOutputExcel_1 = writableSheet_tFileOutputExcel_1
.getColumnView(i_tFileOutputExcel_1).getSize();
fitWidth_tFileOutputExcel_1[i_tFileOutputExcel_1] = fitCellViewSize_tFileOutputExcel_1 / 256;
if (fitCellViewSize_tFileOutputExcel_1 % 256 != 0) {
fitWidth_tFileOutputExcel_1[i_tFileOutputExcel_1] += 1;
}
}
final jxl.write.WritableCellFormat cell_format_last_modified_date_tFileOutputExcel_1 = new jxl.write.WritableCellFormat(
new jxl.write.DateFormat("dd-MM-yyyy"));
ok_Hash.put("tFilterRow_1", false);
start_Hash.put("tFilterRow_1", System.currentTimeMillis());
currentComponent = "tFilterRow_1";
int tos_count_tFilterRow_1 = 0;
int nb_line_tFilterRow_1 = 0;
int nb_line_ok_tFilterRow_1 = 0;
int nb_line_reject_tFilterRow_1 = 0;
class Operator_tFilterRow_1 {
private String sErrorMsg = "";
private boolean bMatchFlag = true;
private String sUnionFlag = "&&";
public Operator_tFilterRow_1(String unionFlag) {
sUnionFlag = unionFlag;
bMatchFlag = "||".equals(unionFlag) ? false : true;
}
public String getErrorMsg() {
if (sErrorMsg != null && sErrorMsg.length() > 1)
return sErrorMsg.substring(1);
else
return null;
}
public boolean getMatchFlag() {
return bMatchFlag;
}
public void matches(boolean partMatched, String reason) {
if ("||".equals(sUnionFlag) && bMatchFlag) {
return;
}
if (!partMatched) {
sErrorMsg += "|" + reason;
}
if ("||".equals(sUnionFlag))
bMatchFlag = bMatchFlag || partMatched;
else
bMatchFlag = bMatchFlag && partMatched;
}
}
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
.decryptPassword("enc:routine.encryption.key.v1:jGIIFoSYB2EzSedfT+a7m+bmjpiH5IyIUffZ+JjtRK4=");
String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;
String url_tDBInput_1 = "jdbc:postgresql:
conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
dbPwd_tDBInput_1);
conn_tDBInput_1.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();
String dbquery_tDBInput_1 = "SELECT * FROM dev_1.users_audit;";
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
row1.rev = 0;
} else {
row1.rev = rs_tDBInput_1.getLong(1);
if (rs_tDBInput_1.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_1 < 2) {
row1.revtype = null;
} else {
row1.revtype = rs_tDBInput_1.getShort(2);
if (rs_tDBInput_1.wasNull()) {
row1.revtype = null;
}
}
if (colQtyInRs_tDBInput_1 < 3) {
row1.id = null;
} else {
row1.id = rs_tDBInput_1.getLong(3);
if (rs_tDBInput_1.wasNull()) {
row1.id = null;
}
}
if (colQtyInRs_tDBInput_1 < 4) {
row1.username = null;
} else {
row1.username = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
}
if (colQtyInRs_tDBInput_1 < 5) {
row1.email = null;
} else {
row1.email = routines.system.JDBCUtil.getString(rs_tDBInput_1, 5, false);
}
if (colQtyInRs_tDBInput_1 < 6) {
row1.avatar_url = null;
} else {
row1.avatar_url = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, false);
}
if (colQtyInRs_tDBInput_1 < 7) {
row1.enabled = null;
} else {
row1.enabled = rs_tDBInput_1.getBoolean(7);
if (rs_tDBInput_1.wasNull()) {
row1.enabled = null;
}
}
if (colQtyInRs_tDBInput_1 < 8) {
row1.is_admin = null;
} else {
row1.is_admin = rs_tDBInput_1.getInt(8);
if (rs_tDBInput_1.wasNull()) {
row1.is_admin = null;
}
}
if (colQtyInRs_tDBInput_1 < 9) {
row1.last_modified_date = null;
} else {
row1.last_modified_date = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 9);
}
if (colQtyInRs_tDBInput_1 < 10) {
row1.last_modified_by = null;
} else {
row1.last_modified_by = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, false);
}
currentComponent = "tDBInput_1";
tos_count_tDBInput_1++;
currentComponent = "tDBInput_1";
currentComponent = "tFilterRow_1";
row3 = null;
Operator_tFilterRow_1 ope_tFilterRow_1 = new Operator_tFilterRow_1("||");
if (ope_tFilterRow_1.getMatchFlag()) {
if (row3 == null) {
row3 = new row3Struct();
}
row3.rev = row1.rev;
row3.revtype = row1.revtype;
row3.id = row1.id;
row3.username = row1.username;
row3.email = row1.email;
row3.avatar_url = row1.avatar_url;
row3.enabled = row1.enabled;
row3.is_admin = row1.is_admin;
row3.last_modified_date = row1.last_modified_date;
row3.last_modified_by = row1.last_modified_by;
nb_line_ok_tFilterRow_1++;
} else {
nb_line_reject_tFilterRow_1++;
}
nb_line_tFilterRow_1++;
tos_count_tFilterRow_1++;
currentComponent = "tFilterRow_1";
if (row3 != null) {
currentComponent = "tFileOutputExcel_1";
columnIndex_tFileOutputExcel_1 = 0;
jxl.write.WritableCell cell_0_tFileOutputExcel_1 = new jxl.write.Number(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.rev);
writableSheet_tFileOutputExcel_1.addCell(cell_0_tFileOutputExcel_1);
int currentWith_0_tFileOutputExcel_1 = String
.valueOf(((jxl.write.Number) cell_0_tFileOutputExcel_1).getValue()).trim().length();
currentWith_0_tFileOutputExcel_1 = currentWith_0_tFileOutputExcel_1 > 10 ? 10
: currentWith_0_tFileOutputExcel_1;
fitWidth_tFileOutputExcel_1[0] = fitWidth_tFileOutputExcel_1[0] > currentWith_0_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[0]
: currentWith_0_tFileOutputExcel_1 + 2;
if (row3.revtype != null) {
columnIndex_tFileOutputExcel_1 = 1;
jxl.write.WritableCell cell_1_tFileOutputExcel_1 = new jxl.write.Number(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.revtype);
writableSheet_tFileOutputExcel_1.addCell(cell_1_tFileOutputExcel_1);
int currentWith_1_tFileOutputExcel_1 = String
.valueOf(((jxl.write.Number) cell_1_tFileOutputExcel_1).getValue()).trim()
.length();
currentWith_1_tFileOutputExcel_1 = currentWith_1_tFileOutputExcel_1 > 10 ? 10
: currentWith_1_tFileOutputExcel_1;
fitWidth_tFileOutputExcel_1[1] = fitWidth_tFileOutputExcel_1[1] > currentWith_1_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[1]
: currentWith_1_tFileOutputExcel_1 + 2;
}
if (row3.id != null) {
columnIndex_tFileOutputExcel_1 = 2;
jxl.write.WritableCell cell_2_tFileOutputExcel_1 = new jxl.write.Number(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.id);
writableSheet_tFileOutputExcel_1.addCell(cell_2_tFileOutputExcel_1);
int currentWith_2_tFileOutputExcel_1 = String
.valueOf(((jxl.write.Number) cell_2_tFileOutputExcel_1).getValue()).trim()
.length();
currentWith_2_tFileOutputExcel_1 = currentWith_2_tFileOutputExcel_1 > 10 ? 10
: currentWith_2_tFileOutputExcel_1;
fitWidth_tFileOutputExcel_1[2] = fitWidth_tFileOutputExcel_1[2] > currentWith_2_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[2]
: currentWith_2_tFileOutputExcel_1 + 2;
}
if (row3.username != null) {
columnIndex_tFileOutputExcel_1 = 3;
jxl.write.WritableCell cell_3_tFileOutputExcel_1 = new jxl.write.Label(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.username);
writableSheet_tFileOutputExcel_1.addCell(cell_3_tFileOutputExcel_1);
int currentWith_3_tFileOutputExcel_1 = cell_3_tFileOutputExcel_1.getContents().trim()
.length();
fitWidth_tFileOutputExcel_1[3] = fitWidth_tFileOutputExcel_1[3] > currentWith_3_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[3]
: currentWith_3_tFileOutputExcel_1 + 2;
}
if (row3.email != null) {
columnIndex_tFileOutputExcel_1 = 4;
jxl.write.WritableCell cell_4_tFileOutputExcel_1 = new jxl.write.Label(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.email);
writableSheet_tFileOutputExcel_1.addCell(cell_4_tFileOutputExcel_1);
int currentWith_4_tFileOutputExcel_1 = cell_4_tFileOutputExcel_1.getContents().trim()
.length();
fitWidth_tFileOutputExcel_1[4] = fitWidth_tFileOutputExcel_1[4] > currentWith_4_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[4]
: currentWith_4_tFileOutputExcel_1 + 2;
}
if (row3.avatar_url != null) {
columnIndex_tFileOutputExcel_1 = 5;
jxl.write.WritableCell cell_5_tFileOutputExcel_1 = new jxl.write.Label(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.avatar_url);
writableSheet_tFileOutputExcel_1.addCell(cell_5_tFileOutputExcel_1);
int currentWith_5_tFileOutputExcel_1 = cell_5_tFileOutputExcel_1.getContents().trim()
.length();
fitWidth_tFileOutputExcel_1[5] = fitWidth_tFileOutputExcel_1[5] > currentWith_5_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[5]
: currentWith_5_tFileOutputExcel_1 + 2;
}
if (row3.enabled != null) {
columnIndex_tFileOutputExcel_1 = 6;
jxl.write.WritableCell cell_6_tFileOutputExcel_1 = new jxl.write.Boolean(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.enabled);
writableSheet_tFileOutputExcel_1.addCell(cell_6_tFileOutputExcel_1);
int currentWith_6_tFileOutputExcel_1 = cell_6_tFileOutputExcel_1.getContents().trim()
.length();
currentWith_6_tFileOutputExcel_1 = 5;
fitWidth_tFileOutputExcel_1[6] = fitWidth_tFileOutputExcel_1[6] > currentWith_6_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[6]
: currentWith_6_tFileOutputExcel_1 + 2;
}
if (row3.is_admin != null) {
columnIndex_tFileOutputExcel_1 = 7;
jxl.write.WritableCell cell_7_tFileOutputExcel_1 = new jxl.write.Number(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.is_admin);
writableSheet_tFileOutputExcel_1.addCell(cell_7_tFileOutputExcel_1);
int currentWith_7_tFileOutputExcel_1 = String
.valueOf(((jxl.write.Number) cell_7_tFileOutputExcel_1).getValue()).trim()
.length();
currentWith_7_tFileOutputExcel_1 = currentWith_7_tFileOutputExcel_1 > 10 ? 10
: currentWith_7_tFileOutputExcel_1;
fitWidth_tFileOutputExcel_1[7] = fitWidth_tFileOutputExcel_1[7] > currentWith_7_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[7]
: currentWith_7_tFileOutputExcel_1 + 2;
}
if (row3.last_modified_date != null) {
columnIndex_tFileOutputExcel_1 = 8;
jxl.write.WritableCell cell_8_tFileOutputExcel_1 = new jxl.write.DateTime(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.last_modified_date, cell_format_last_modified_date_tFileOutputExcel_1);
writableSheet_tFileOutputExcel_1.addCell(cell_8_tFileOutputExcel_1);
int currentWith_8_tFileOutputExcel_1 = cell_8_tFileOutputExcel_1.getContents().trim()
.length();
currentWith_8_tFileOutputExcel_1 = 12;
fitWidth_tFileOutputExcel_1[8] = fitWidth_tFileOutputExcel_1[8] > currentWith_8_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[8]
: currentWith_8_tFileOutputExcel_1 + 2;
}
if (row3.last_modified_by != null) {
columnIndex_tFileOutputExcel_1 = 9;
jxl.write.WritableCell cell_9_tFileOutputExcel_1 = new jxl.write.Label(
columnIndex_tFileOutputExcel_1,
startRowNum_tFileOutputExcel_1 + nb_line_tFileOutputExcel_1,
row3.last_modified_by);
writableSheet_tFileOutputExcel_1.addCell(cell_9_tFileOutputExcel_1);
int currentWith_9_tFileOutputExcel_1 = cell_9_tFileOutputExcel_1.getContents().trim()
.length();
fitWidth_tFileOutputExcel_1[9] = fitWidth_tFileOutputExcel_1[9] > currentWith_9_tFileOutputExcel_1
? fitWidth_tFileOutputExcel_1[9]
: currentWith_9_tFileOutputExcel_1 + 2;
}
nb_line_tFileOutputExcel_1++;
tos_count_tFileOutputExcel_1++;
currentComponent = "tFileOutputExcel_1";
currentComponent = "tFileOutputExcel_1";
}
currentComponent = "tFilterRow_1";
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
currentComponent = "tFilterRow_1";
globalMap.put("tFilterRow_1_NB_LINE", nb_line_tFilterRow_1);
globalMap.put("tFilterRow_1_NB_LINE_OK", nb_line_ok_tFilterRow_1);
globalMap.put("tFilterRow_1_NB_LINE_REJECT", nb_line_reject_tFilterRow_1);
ok_Hash.put("tFilterRow_1", true);
end_Hash.put("tFilterRow_1", System.currentTimeMillis());
currentComponent = "tFileOutputExcel_1";
writeableWorkbook_tFileOutputExcel_1.write();
writeableWorkbook_tFileOutputExcel_1.close();
if (headerIsInserted_tFileOutputExcel_1 && nb_line_tFileOutputExcel_1 > 0) {
nb_line_tFileOutputExcel_1 = nb_line_tFileOutputExcel_1 - 1;
}
globalMap.put("tFileOutputExcel_1_NB_LINE", nb_line_tFileOutputExcel_1);
ok_Hash.put("tFileOutputExcel_1", true);
end_Hash.put("tFileOutputExcel_1", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
throw error;
} finally {
try {
currentComponent = "tDBInput_1";
currentComponent = "tFilterRow_1";
currentComponent = "tFileOutputExcel_1";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
}
public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
final static byte[] commonByteArrayLock_TALYS_AuditTrailReport = new byte[0];
static byte[] commonByteArray_TALYS_AuditTrailReport = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long rev;
public long getRev() {
return this.rev;
}
public Short revtype;
public Short getRevtype() {
return this.revtype;
}
public Long id;
public Long getId() {
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
public java.util.Date last_modified_date;
public java.util.Date getLast_modified_date() {
return this.last_modified_date;
}
public String last_modified_by;
public String getLast_modified_by() {
return this.last_modified_by;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.rev;
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
if (this.rev != other.rev)
return false;
return true;
}
public void copyDataTo(row2Struct other) {
other.rev = this.rev;
other.revtype = this.revtype;
other.id = this.id;
other.name = this.name;
other.description = this.description;
other.last_modified_date = this.last_modified_date;
other.last_modified_by = this.last_modified_by;
}
public void copyKeysDataTo(row2Struct other) {
other.rev = this.rev;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_AuditTrailReport.length) {
if (length < 1024 && commonByteArray_TALYS_AuditTrailReport.length == 0) {
commonByteArray_TALYS_AuditTrailReport = new byte[1024];
} else {
commonByteArray_TALYS_AuditTrailReport = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_AuditTrailReport, 0, length);
strReturn = new String(commonByteArray_TALYS_AuditTrailReport, 0, length, utf8Charset);
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
if (length > commonByteArray_TALYS_AuditTrailReport.length) {
if (length < 1024 && commonByteArray_TALYS_AuditTrailReport.length == 0) {
commonByteArray_TALYS_AuditTrailReport = new byte[1024];
} else {
commonByteArray_TALYS_AuditTrailReport = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_AuditTrailReport, 0, length);
strReturn = new String(commonByteArray_TALYS_AuditTrailReport, 0, length, utf8Charset);
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
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_AuditTrailReport) {
try {
int length = 0;
this.rev = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.revtype = null;
} else {
this.revtype = dis.readShort();
}
length = dis.readByte();
if (length == -1) {
this.id = null;
} else {
this.id = dis.readLong();
}
this.name = readString(dis);
this.description = readString(dis);
this.last_modified_date = readDate(dis);
this.last_modified_by = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_AuditTrailReport) {
try {
int length = 0;
this.rev = dis.readLong();
length = dis.readByte();
if (length == -1) {
this.revtype = null;
} else {
this.revtype = dis.readShort();
}
length = dis.readByte();
if (length == -1) {
this.id = null;
} else {
this.id = dis.readLong();
}
this.name = readString(dis);
this.description = readString(dis);
this.last_modified_date = readDate(dis);
this.last_modified_by = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.rev);
if (this.revtype == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeShort(this.revtype);
}
if (this.id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.id);
}
writeString(this.name, dos);
writeString(this.description, dos);
writeDate(this.last_modified_date, dos);
writeString(this.last_modified_by, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.rev);
if (this.revtype == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeShort(this.revtype);
}
if (this.id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.id);
}
writeString(this.name, dos);
writeString(this.description, dos);
writeDate(this.last_modified_date, dos);
writeString(this.last_modified_by, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("rev=" + String.valueOf(rev));
sb.append(",revtype=" + String.valueOf(revtype));
sb.append(",id=" + String.valueOf(id));
sb.append(",name=" + name);
sb.append(",description=" + description);
sb.append(",last_modified_date=" + String.valueOf(last_modified_date));
sb.append(",last_modified_by=" + last_modified_by);
sb.append("]");
return sb.toString();
}
public int compareTo(row2Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.rev, other.rev);
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
row2Struct row2 = new row2Struct();
ok_Hash.put("tFileOutputExcel_2", false);
start_Hash.put("tFileOutputExcel_2", System.currentTimeMillis());
currentComponent = "tFileOutputExcel_2";
int tos_count_tFileOutputExcel_2 = 0;
int columnIndex_tFileOutputExcel_2 = 0;
boolean headerIsInserted_tFileOutputExcel_2 = false;
int nb_line_tFileOutputExcel_2 = 0;
String fileName_tFileOutputExcel_2 = "C:/Users/pc/Desktop/out.xls";
java.io.File file_tFileOutputExcel_2 = new java.io.File(fileName_tFileOutputExcel_2);
boolean isFileGenerated_tFileOutputExcel_2 = true;
java.io.File parentFile_tFileOutputExcel_2 = file_tFileOutputExcel_2.getParentFile();
if (parentFile_tFileOutputExcel_2 != null && !parentFile_tFileOutputExcel_2.exists()) {
parentFile_tFileOutputExcel_2.mkdirs();
}
jxl.write.WritableWorkbook writeableWorkbook_tFileOutputExcel_2 = null;
jxl.write.WritableSheet writableSheet_tFileOutputExcel_2 = null;
jxl.WorkbookSettings workbookSettings_tFileOutputExcel_2 = new jxl.WorkbookSettings();
workbookSettings_tFileOutputExcel_2.setEncoding("ISO-8859-15");
writeableWorkbook_tFileOutputExcel_2 = new jxl.write.biff.WritableWorkbookImpl(
new java.io.BufferedOutputStream(new java.io.FileOutputStream(fileName_tFileOutputExcel_2)),
true, workbookSettings_tFileOutputExcel_2);
writableSheet_tFileOutputExcel_2 = writeableWorkbook_tFileOutputExcel_2.getSheet("Sheet2");
if (writableSheet_tFileOutputExcel_2 == null) {
writableSheet_tFileOutputExcel_2 = writeableWorkbook_tFileOutputExcel_2.createSheet("Sheet2",
writeableWorkbook_tFileOutputExcel_2.getNumberOfSheets());
}
int startRowNum_tFileOutputExcel_2 = writableSheet_tFileOutputExcel_2.getRows();
int[] fitWidth_tFileOutputExcel_2 = new int[7];
for (int i_tFileOutputExcel_2 = 0; i_tFileOutputExcel_2 < 7; i_tFileOutputExcel_2++) {
int fitCellViewSize_tFileOutputExcel_2 = writableSheet_tFileOutputExcel_2
.getColumnView(i_tFileOutputExcel_2).getSize();
fitWidth_tFileOutputExcel_2[i_tFileOutputExcel_2] = fitCellViewSize_tFileOutputExcel_2 / 256;
if (fitCellViewSize_tFileOutputExcel_2 % 256 != 0) {
fitWidth_tFileOutputExcel_2[i_tFileOutputExcel_2] += 1;
}
}
final jxl.write.WritableCellFormat cell_format_last_modified_date_tFileOutputExcel_2 = new jxl.write.WritableCellFormat(
new jxl.write.DateFormat("dd-MM-yyyy"));
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
.decryptPassword("enc:routine.encryption.key.v1:u+aoMPyWl9gAefJYol3ylrcCPRgqzHVDrmQgwWnzvMU=");
String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;
String url_tDBInput_2 = "jdbc:postgresql:
conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
dbPwd_tDBInput_2);
conn_tDBInput_2.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();
String dbquery_tDBInput_2 = "SELECT * FROM dev_1.business_domains_audit;";
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
row2.rev = 0;
} else {
row2.rev = rs_tDBInput_2.getLong(1);
if (rs_tDBInput_2.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_2 < 2) {
row2.revtype = null;
} else {
row2.revtype = rs_tDBInput_2.getShort(2);
if (rs_tDBInput_2.wasNull()) {
row2.revtype = null;
}
}
if (colQtyInRs_tDBInput_2 < 3) {
row2.id = null;
} else {
row2.id = rs_tDBInput_2.getLong(3);
if (rs_tDBInput_2.wasNull()) {
row2.id = null;
}
}
if (colQtyInRs_tDBInput_2 < 4) {
row2.name = null;
} else {
row2.name = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
}
if (colQtyInRs_tDBInput_2 < 5) {
row2.description = null;
} else {
row2.description = routines.system.JDBCUtil.getString(rs_tDBInput_2, 5, false);
}
if (colQtyInRs_tDBInput_2 < 6) {
row2.last_modified_date = null;
} else {
row2.last_modified_date = routines.system.JDBCUtil.getDate(rs_tDBInput_2, 6);
}
if (colQtyInRs_tDBInput_2 < 7) {
row2.last_modified_by = null;
} else {
row2.last_modified_by = routines.system.JDBCUtil.getString(rs_tDBInput_2, 7, false);
}
currentComponent = "tDBInput_2";
tos_count_tDBInput_2++;
currentComponent = "tDBInput_2";
currentComponent = "tFileOutputExcel_2";
columnIndex_tFileOutputExcel_2 = 0;
jxl.write.WritableCell cell_0_tFileOutputExcel_2 = new jxl.write.Number(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.rev);
writableSheet_tFileOutputExcel_2.addCell(cell_0_tFileOutputExcel_2);
int currentWith_0_tFileOutputExcel_2 = String
.valueOf(((jxl.write.Number) cell_0_tFileOutputExcel_2).getValue()).trim().length();
currentWith_0_tFileOutputExcel_2 = currentWith_0_tFileOutputExcel_2 > 10 ? 10
: currentWith_0_tFileOutputExcel_2;
fitWidth_tFileOutputExcel_2[0] = fitWidth_tFileOutputExcel_2[0] > currentWith_0_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[0]
: currentWith_0_tFileOutputExcel_2 + 2;
if (row2.revtype != null) {
columnIndex_tFileOutputExcel_2 = 1;
jxl.write.WritableCell cell_1_tFileOutputExcel_2 = new jxl.write.Number(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.revtype);
writableSheet_tFileOutputExcel_2.addCell(cell_1_tFileOutputExcel_2);
int currentWith_1_tFileOutputExcel_2 = String
.valueOf(((jxl.write.Number) cell_1_tFileOutputExcel_2).getValue()).trim().length();
currentWith_1_tFileOutputExcel_2 = currentWith_1_tFileOutputExcel_2 > 10 ? 10
: currentWith_1_tFileOutputExcel_2;
fitWidth_tFileOutputExcel_2[1] = fitWidth_tFileOutputExcel_2[1] > currentWith_1_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[1]
: currentWith_1_tFileOutputExcel_2 + 2;
}
if (row2.id != null) {
columnIndex_tFileOutputExcel_2 = 2;
jxl.write.WritableCell cell_2_tFileOutputExcel_2 = new jxl.write.Number(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.id);
writableSheet_tFileOutputExcel_2.addCell(cell_2_tFileOutputExcel_2);
int currentWith_2_tFileOutputExcel_2 = String
.valueOf(((jxl.write.Number) cell_2_tFileOutputExcel_2).getValue()).trim().length();
currentWith_2_tFileOutputExcel_2 = currentWith_2_tFileOutputExcel_2 > 10 ? 10
: currentWith_2_tFileOutputExcel_2;
fitWidth_tFileOutputExcel_2[2] = fitWidth_tFileOutputExcel_2[2] > currentWith_2_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[2]
: currentWith_2_tFileOutputExcel_2 + 2;
}
if (row2.name != null) {
columnIndex_tFileOutputExcel_2 = 3;
jxl.write.WritableCell cell_3_tFileOutputExcel_2 = new jxl.write.Label(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.name);
writableSheet_tFileOutputExcel_2.addCell(cell_3_tFileOutputExcel_2);
int currentWith_3_tFileOutputExcel_2 = cell_3_tFileOutputExcel_2.getContents().trim()
.length();
fitWidth_tFileOutputExcel_2[3] = fitWidth_tFileOutputExcel_2[3] > currentWith_3_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[3]
: currentWith_3_tFileOutputExcel_2 + 2;
}
if (row2.description != null) {
columnIndex_tFileOutputExcel_2 = 4;
jxl.write.WritableCell cell_4_tFileOutputExcel_2 = new jxl.write.Label(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.description);
writableSheet_tFileOutputExcel_2.addCell(cell_4_tFileOutputExcel_2);
int currentWith_4_tFileOutputExcel_2 = cell_4_tFileOutputExcel_2.getContents().trim()
.length();
fitWidth_tFileOutputExcel_2[4] = fitWidth_tFileOutputExcel_2[4] > currentWith_4_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[4]
: currentWith_4_tFileOutputExcel_2 + 2;
}
if (row2.last_modified_date != null) {
columnIndex_tFileOutputExcel_2 = 5;
jxl.write.WritableCell cell_5_tFileOutputExcel_2 = new jxl.write.DateTime(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.last_modified_date, cell_format_last_modified_date_tFileOutputExcel_2);
writableSheet_tFileOutputExcel_2.addCell(cell_5_tFileOutputExcel_2);
int currentWith_5_tFileOutputExcel_2 = cell_5_tFileOutputExcel_2.getContents().trim()
.length();
currentWith_5_tFileOutputExcel_2 = 12;
fitWidth_tFileOutputExcel_2[5] = fitWidth_tFileOutputExcel_2[5] > currentWith_5_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[5]
: currentWith_5_tFileOutputExcel_2 + 2;
}
if (row2.last_modified_by != null) {
columnIndex_tFileOutputExcel_2 = 6;
jxl.write.WritableCell cell_6_tFileOutputExcel_2 = new jxl.write.Label(
columnIndex_tFileOutputExcel_2,
startRowNum_tFileOutputExcel_2 + nb_line_tFileOutputExcel_2,
row2.last_modified_by);
writableSheet_tFileOutputExcel_2.addCell(cell_6_tFileOutputExcel_2);
int currentWith_6_tFileOutputExcel_2 = cell_6_tFileOutputExcel_2.getContents().trim()
.length();
fitWidth_tFileOutputExcel_2[6] = fitWidth_tFileOutputExcel_2[6] > currentWith_6_tFileOutputExcel_2
? fitWidth_tFileOutputExcel_2[6]
: currentWith_6_tFileOutputExcel_2 + 2;
}
nb_line_tFileOutputExcel_2++;
tos_count_tFileOutputExcel_2++;
currentComponent = "tFileOutputExcel_2";
currentComponent = "tFileOutputExcel_2";
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
currentComponent = "tFileOutputExcel_2";
columnIndex_tFileOutputExcel_2 = 0;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[0]);
columnIndex_tFileOutputExcel_2 = 1;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[1]);
columnIndex_tFileOutputExcel_2 = 2;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[2]);
columnIndex_tFileOutputExcel_2 = 3;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[3]);
columnIndex_tFileOutputExcel_2 = 4;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[4]);
columnIndex_tFileOutputExcel_2 = 5;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[5]);
columnIndex_tFileOutputExcel_2 = 6;
writableSheet_tFileOutputExcel_2.setColumnView(columnIndex_tFileOutputExcel_2,
fitWidth_tFileOutputExcel_2[6]);
writeableWorkbook_tFileOutputExcel_2.write();
writeableWorkbook_tFileOutputExcel_2.close();
if (headerIsInserted_tFileOutputExcel_2 && nb_line_tFileOutputExcel_2 > 0) {
nb_line_tFileOutputExcel_2 = nb_line_tFileOutputExcel_2 - 1;
}
globalMap.put("tFileOutputExcel_2_NB_LINE", nb_line_tFileOutputExcel_2);
ok_Hash.put("tFileOutputExcel_2", true);
end_Hash.put("tFileOutputExcel_2", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
throw error;
} finally {
try {
currentComponent = "tDBInput_2";
currentComponent = "tFileOutputExcel_2";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
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
final AuditTrailReport AuditTrailReportClass = new AuditTrailReport();
int exitCode = AuditTrailReportClass.runJobInTOS(args);
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
java.io.InputStream inContext = AuditTrailReport.class.getClassLoader()
.getResourceAsStream("talys/audittrailreport_0_1/contexts/" + contextStr + ".properties");
if (inContext == null) {
inContext = AuditTrailReport.class.getClassLoader()
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
tDBInput_1Process(globalMap);
if (!"failure".equals(status)) {
status = "end";
}
} catch (TalendException e_tDBInput_1) {
globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);
e_tDBInput_1.printStackTrace();
}
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
System.out.println(
(endUsedMemory - startUsedMemory) + " bytes memory increase when running : AuditTrailReport");
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