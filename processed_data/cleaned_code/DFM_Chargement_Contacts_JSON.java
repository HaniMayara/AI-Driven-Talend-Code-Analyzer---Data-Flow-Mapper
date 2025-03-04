package dfm.dfm_chargement_contacts_json_0_1;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.Mathematical;
import routines.DataQualityDependencies;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.DQTechnical;
import routines.TalendDate;
import routines.DataMasking;
import routines.DqStringHandling;
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
public class DFM_Chargement_Contacts_JSON implements TalendJob {
static {
System.setProperty("TalendJob.log", "DFM_Chargement_Contacts_JSON.log");
}
private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
.getLogger(DFM_Chargement_Contacts_JSON.class);
protected static void logIgnoredError(String message, Throwable cause) {
log.error(message, cause);
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
private final String jobName = "DFM_Chargement_Contacts_JSON";
private final String projectName = "DFM";
public Integer errorCode = null;
private String currentComponent = "";
private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
"_9hKJ0OiBEe-lqsBLdiLUvA", "0.1");
private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;
private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));
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
DFM_Chargement_Contacts_JSON.this.exception = e;
}
}
if (!(e instanceof TalendException)) {
try {
for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
if (m.getName().compareTo(currentComponent + "_error") == 0) {
m.invoke(DFM_Chargement_Contacts_JSON.this,
new Object[] { e, currentComponent, globalMap });
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
public void tFileInputJSON_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputJSON_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputJSON_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputJSON_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputJSON_1_onSubJobError(exception, errorComponent, globalMap);
}
public void talendJobLog_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
talendJobLog_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileInputJSON_1_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public static class ContextBean {
static String evaluate(String context, String contextExpression, String jobName)
throws IOException, javax.script.ScriptException {
String currentContext = context;
String jobNameStripped = jobName.substring(jobName.lastIndexOf(".") + 1);
boolean inOSGi = routines.system.BundleUtils.inOSGi();
java.util.Dictionary<String, Object> jobProperties = null;
if (inOSGi) {
jobProperties = routines.system.BundleUtils.getJobProperties(jobNameStripped);
if (jobProperties != null && null != jobProperties.get("context")) {
currentContext = (String) jobProperties.get("context");
}
}
boolean isExpression = contextExpression.contains("+") || contextExpression.contains("(");
final String prefix = isExpression ? "\"" : "";
java.util.Properties defaultProps = new java.util.Properties();
java.io.InputStream inContext = DFM_Chargement_Contacts_JSON.class.getClassLoader().getResourceAsStream(
"dfm/dfm_chargement_contacts_json_0_1/contexts/" + currentContext + ".properties");
if (inContext == null) {
inContext = DFM_Chargement_Contacts_JSON.class.getClassLoader()
.getResourceAsStream("config/contexts/" + currentContext + ".properties");
}
try {
defaultProps.load(inContext);
if (jobProperties != null) {
java.util.Enumeration<String> keys = jobProperties.keys();
while (keys.hasMoreElements()) {
String propKey = keys.nextElement();
if (defaultProps.containsKey(propKey)) {
defaultProps.put(propKey, (String) jobProperties.get(propKey));
}
}
}
} finally {
inContext.close();
}
java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("context.([\\w]+)");
java.util.regex.Matcher matcher = pattern.matcher(contextExpression);
while (matcher.find()) {
contextExpression = contextExpression.replaceAll(matcher.group(0),
prefix + defaultProps.getProperty(matcher.group(1)) + prefix);
}
if (contextExpression.startsWith("/services")) {
contextExpression = contextExpression.replaceFirst("/services", "");
}
return isExpression ? evaluateContextExpression(contextExpression) : contextExpression;
}
public static String evaluateContextExpression(String expression) throws javax.script.ScriptException {
javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
javax.script.ScriptEngine engine = manager.getEngineByName("nashorn");
expression = expression.replaceAll("System.getProperty", "java.lang.System.getProperty");
return engine.eval(expression).toString();
}
public static String getContext(String context, String contextName, String jobName) throws Exception {
return contextName.contains("context.") ? evaluate(context, contextName, jobName) : contextName;
}
}
public static class OUTStruct implements routines.system.IPersistableRow<OUTStruct> {
final static byte[] commonByteArrayLock_DFM_DFM_Chargement_Contacts_JSON = new byte[0];
static byte[] commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[0];
public String Nom;
public String getNom() {
return this.Nom;
}
public Boolean NomIsNullable() {
return true;
}
public Boolean NomIsKey() {
return false;
}
public Integer NomLength() {
return null;
}
public Integer NomPrecision() {
return null;
}
public String NomDefault() {
return null;
}
public String NomComment() {
return "";
}
public String NomPattern() {
return "";
}
public String NomOriginalDbColumnName() {
return "Nom";
}
public String Prenom;
public String getPrenom() {
return this.Prenom;
}
public Boolean PrenomIsNullable() {
return true;
}
public Boolean PrenomIsKey() {
return false;
}
public Integer PrenomLength() {
return null;
}
public Integer PrenomPrecision() {
return null;
}
public String PrenomDefault() {
return null;
}
public String PrenomComment() {
return "";
}
public String PrenomPattern() {
return "";
}
public String PrenomOriginalDbColumnName() {
return "Prenom";
}
public String Num_Telephone;
public String getNum_Telephone() {
return this.Num_Telephone;
}
public Boolean Num_TelephoneIsNullable() {
return true;
}
public Boolean Num_TelephoneIsKey() {
return false;
}
public Integer Num_TelephoneLength() {
return null;
}
public Integer Num_TelephonePrecision() {
return null;
}
public String Num_TelephoneDefault() {
return null;
}
public String Num_TelephoneComment() {
return "";
}
public String Num_TelephonePattern() {
return "";
}
public String Num_TelephoneOriginalDbColumnName() {
return "Num_Telephone";
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length) {
if (length < 1024 && commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length == 0) {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[1024];
} else {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[2 * length];
}
}
dis.readFully(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length);
strReturn = new String(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length, utf8Charset);
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
if (length > commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length) {
if (length < 1024 && commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length == 0) {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[1024];
} else {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length);
strReturn = new String(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length, utf8Charset);
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
synchronized (commonByteArrayLock_DFM_DFM_Chargement_Contacts_JSON) {
try {
int length = 0;
this.Nom = readString(dis);
this.Prenom = readString(dis);
this.Num_Telephone = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_DFM_DFM_Chargement_Contacts_JSON) {
try {
int length = 0;
this.Nom = readString(dis);
this.Prenom = readString(dis);
this.Num_Telephone = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.Nom, dos);
writeString(this.Prenom, dos);
writeString(this.Num_Telephone, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.Nom, dos);
writeString(this.Prenom, dos);
writeString(this.Num_Telephone, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("Nom=" + Nom);
sb.append(",Prenom=" + Prenom);
sb.append(",Num_Telephone=" + Num_Telephone);
sb.append("]");
return sb.toString();
}
public String toLogString() {
StringBuilder sb = new StringBuilder();
if (Nom == null) {
sb.append("<null>");
} else {
sb.append(Nom);
}
sb.append("|");
if (Prenom == null) {
sb.append("<null>");
} else {
sb.append(Prenom);
}
sb.append("|");
if (Num_Telephone == null) {
sb.append("<null>");
} else {
sb.append(Num_Telephone);
}
sb.append("|");
return sb.toString();
}
public int compareTo(OUTStruct other) {
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
final static byte[] commonByteArrayLock_DFM_DFM_Chargement_Contacts_JSON = new byte[0];
static byte[] commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[0];
public String Nom;
public String getNom() {
return this.Nom;
}
public Boolean NomIsNullable() {
return true;
}
public Boolean NomIsKey() {
return false;
}
public Integer NomLength() {
return null;
}
public Integer NomPrecision() {
return null;
}
public String NomDefault() {
return null;
}
public String NomComment() {
return "";
}
public String NomPattern() {
return "";
}
public String NomOriginalDbColumnName() {
return "Nom";
}
public String Prenom;
public String getPrenom() {
return this.Prenom;
}
public Boolean PrenomIsNullable() {
return true;
}
public Boolean PrenomIsKey() {
return false;
}
public Integer PrenomLength() {
return null;
}
public Integer PrenomPrecision() {
return null;
}
public String PrenomDefault() {
return null;
}
public String PrenomComment() {
return "";
}
public String PrenomPattern() {
return "";
}
public String PrenomOriginalDbColumnName() {
return "Prenom";
}
public String Num_Telephone;
public String getNum_Telephone() {
return this.Num_Telephone;
}
public Boolean Num_TelephoneIsNullable() {
return true;
}
public Boolean Num_TelephoneIsKey() {
return false;
}
public Integer Num_TelephoneLength() {
return null;
}
public Integer Num_TelephonePrecision() {
return null;
}
public String Num_TelephoneDefault() {
return null;
}
public String Num_TelephoneComment() {
return "";
}
public String Num_TelephonePattern() {
return "";
}
public String Num_TelephoneOriginalDbColumnName() {
return "Num_Telephone";
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length) {
if (length < 1024 && commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length == 0) {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[1024];
} else {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[2 * length];
}
}
dis.readFully(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length);
strReturn = new String(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length, utf8Charset);
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
if (length > commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length) {
if (length < 1024 && commonByteArray_DFM_DFM_Chargement_Contacts_JSON.length == 0) {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[1024];
} else {
commonByteArray_DFM_DFM_Chargement_Contacts_JSON = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length);
strReturn = new String(commonByteArray_DFM_DFM_Chargement_Contacts_JSON, 0, length, utf8Charset);
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
synchronized (commonByteArrayLock_DFM_DFM_Chargement_Contacts_JSON) {
try {
int length = 0;
this.Nom = readString(dis);
this.Prenom = readString(dis);
this.Num_Telephone = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_DFM_DFM_Chargement_Contacts_JSON) {
try {
int length = 0;
this.Nom = readString(dis);
this.Prenom = readString(dis);
this.Num_Telephone = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.Nom, dos);
writeString(this.Prenom, dos);
writeString(this.Num_Telephone, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.Nom, dos);
writeString(this.Prenom, dos);
writeString(this.Num_Telephone, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("Nom=" + Nom);
sb.append(",Prenom=" + Prenom);
sb.append(",Num_Telephone=" + Num_Telephone);
sb.append("]");
return sb.toString();
}
public String toLogString() {
StringBuilder sb = new StringBuilder();
if (Nom == null) {
sb.append("<null>");
} else {
sb.append(Nom);
}
sb.append("|");
if (Prenom == null) {
sb.append("<null>");
} else {
sb.append(Prenom);
}
sb.append("|");
if (Num_Telephone == null) {
sb.append("<null>");
} else {
sb.append(Num_Telephone);
}
sb.append("|");
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
public void tFileInputJSON_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tFileInputJSON_1_SUBPROCESS_STATE", 0);
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
OUTStruct OUT = new OUTStruct();
ok_Hash.put("tFileOutputJSON_1", false);
start_Hash.put("tFileOutputJSON_1", System.currentTimeMillis());
currentComponent = "tFileOutputJSON_1";
runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "OUT");
int tos_count_tFileOutputJSON_1 = 0;
if (log.isDebugEnabled())
log.debug("tFileOutputJSON_1 - " + ("Start to work."));
if (log.isDebugEnabled()) {
class BytesLimit65535_tFileOutputJSON_1 {
public void limitLog4jByte() throws Exception {
StringBuilder log4jParamters_tFileOutputJSON_1 = new StringBuilder();
log4jParamters_tFileOutputJSON_1.append("Parameters:");
log4jParamters_tFileOutputJSON_1
.append("FILENAME" + " = " + "\"D:/DFM/DATA/OUT/Contacts_OUT.json\"");
log4jParamters_tFileOutputJSON_1.append(" | ");
log4jParamters_tFileOutputJSON_1.append("GENERATE_JSON_ARRAY" + " = " + "false");
log4jParamters_tFileOutputJSON_1.append(" | ");
log4jParamters_tFileOutputJSON_1.append("DATABLOCKNAME" + " = " + "\"data\"");
log4jParamters_tFileOutputJSON_1.append(" | ");
log4jParamters_tFileOutputJSON_1.append("CREATE" + " = " + "true");
log4jParamters_tFileOutputJSON_1.append(" | ");
if (log.isDebugEnabled())
log.debug("tFileOutputJSON_1 - " + (log4jParamters_tFileOutputJSON_1));
}
}
new BytesLimit65535_tFileOutputJSON_1().limitLog4jByte();
}
if (enableLogStash) {
talendJobLog.addCM("tFileOutputJSON_1", "tFileOutputJSON_1", "tFileOutputJSON");
talendJobLogProcess(globalMap);
}
int nb_line_tFileOutputJSON_1 = 0;
java.io.File file_tFileOutputJSON_1 = new java.io.File("D:/DFM/DATA/OUT/Contacts_OUT.json");
java.io.File dir_tFileOutputJSON_1 = file_tFileOutputJSON_1.getParentFile();
if (dir_tFileOutputJSON_1 != null && !dir_tFileOutputJSON_1.exists()) {
dir_tFileOutputJSON_1.mkdirs();
}
java.io.PrintWriter outtFileOutputJSON_1 = new java.io.PrintWriter(
new java.io.BufferedWriter(new java.io.FileWriter("D:/DFM/DATA/OUT/Contacts_OUT.json")));
outtFileOutputJSON_1.append("{\"" + "data" + "\":[");
boolean isFirst_tFileOutputJSON_1 = true;
ok_Hash.put("tMap_1", false);
start_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tMap_1";
runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");
int tos_count_tMap_1 = 0;
if (log.isDebugEnabled())
log.debug("tMap_1 - " + ("Start to work."));
if (log.isDebugEnabled()) {
class BytesLimit65535_tMap_1 {
public void limitLog4jByte() throws Exception {
StringBuilder log4jParamters_tMap_1 = new StringBuilder();
log4jParamters_tMap_1.append("Parameters:");
log4jParamters_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
log4jParamters_tMap_1.append(" | ");
log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
log4jParamters_tMap_1.append(" | ");
log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
log4jParamters_tMap_1.append(" | ");
log4jParamters_tMap_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
log4jParamters_tMap_1.append(" | ");
if (log.isDebugEnabled())
log.debug("tMap_1 - " + (log4jParamters_tMap_1));
}
}
new BytesLimit65535_tMap_1().limitLog4jByte();
}
if (enableLogStash) {
talendJobLog.addCM("tMap_1", "tMap_1", "tMap");
talendJobLogProcess(globalMap);
}
int count_row1_tMap_1 = 0;
class Var__tMap_1__Struct {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
int count_OUT_tMap_1 = 0;
OUTStruct OUT_tmp = new OUTStruct();
ok_Hash.put("tFileInputJSON_1", false);
start_Hash.put("tFileInputJSON_1", System.currentTimeMillis());
currentComponent = "tFileInputJSON_1";
int tos_count_tFileInputJSON_1 = 0;
if (log.isDebugEnabled())
log.debug("tFileInputJSON_1 - " + ("Start to work."));
if (log.isDebugEnabled()) {
class BytesLimit65535_tFileInputJSON_1 {
public void limitLog4jByte() throws Exception {
StringBuilder log4jParamters_tFileInputJSON_1 = new StringBuilder();
log4jParamters_tFileInputJSON_1.append("Parameters:");
log4jParamters_tFileInputJSON_1.append("READ_BY" + " = " + "JSONPATH");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("JSON_PATH_VERSION" + " = " + "2_1_0");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("USEURL" + " = " + "false");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1
.append("FILENAME" + " = " + "\"D:/DFM/DATA/IN/Contacts.json\"");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("JSON_LOOP_QUERY" + " = " + "\"$[*] \"");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("MAPPING_JSONPATH" + " = " + "[{QUERY="
+ ("\"$.Nom\"") + ", SCHEMA_COLUMN=" + ("Nom") + "}, {QUERY=" + ("\"$.Prenom\"")
+ ", SCHEMA_COLUMN=" + ("Prenom") + "}, {QUERY=" + ("\"$.Num_Telephone\"")
+ ", SCHEMA_COLUMN=" + ("Num_Telephone") + "}]");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("DIE_ON_ERROR" + " = " + "false");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("ADVANCED_SEPARATOR" + " = " + "false");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("USE_LOOP_AS_ROOT" + " = " + "true");
log4jParamters_tFileInputJSON_1.append(" | ");
log4jParamters_tFileInputJSON_1.append("ENCODING" + " = " + "\"UTF-8\"");
log4jParamters_tFileInputJSON_1.append(" | ");
if (log.isDebugEnabled())
log.debug("tFileInputJSON_1 - " + (log4jParamters_tFileInputJSON_1));
}
}
new BytesLimit65535_tFileInputJSON_1().limitLog4jByte();
}
if (enableLogStash) {
talendJobLog.addCM("tFileInputJSON_1", "tFileInputJSON_1", "tFileInputJSON");
talendJobLogProcess(globalMap);
}
class JsonPathCache_tFileInputJSON_1 {
final java.util.Map<String, com.jayway.jsonpath.JsonPath> jsonPathString2compiledJsonPath = new java.util.HashMap<String, com.jayway.jsonpath.JsonPath>();
public com.jayway.jsonpath.JsonPath getCompiledJsonPath(String jsonPath) {
if (jsonPathString2compiledJsonPath.containsKey(jsonPath)) {
return jsonPathString2compiledJsonPath.get(jsonPath);
} else {
com.jayway.jsonpath.JsonPath compiledLoopPath = com.jayway.jsonpath.JsonPath
.compile(jsonPath);
jsonPathString2compiledJsonPath.put(jsonPath, compiledLoopPath);
return compiledLoopPath;
}
}
}
int nb_line_tFileInputJSON_1 = 0;
JsonPathCache_tFileInputJSON_1 jsonPathCache_tFileInputJSON_1 = new JsonPathCache_tFileInputJSON_1();
String loopPath_tFileInputJSON_1 = "$[*] ";
java.util.List<Object> resultset_tFileInputJSON_1 = new java.util.ArrayList<Object>();
java.io.InputStream is_tFileInputJSON_1 = null;
com.jayway.jsonpath.ParseContext parseContext_tFileInputJSON_1 = com.jayway.jsonpath.JsonPath
.using(com.jayway.jsonpath.Configuration.defaultConfiguration());
Object filenameOrStream_tFileInputJSON_1 = null;
try {
filenameOrStream_tFileInputJSON_1 = "D:/DFM/DATA/IN/Contacts.json";
} catch (java.lang.Exception e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
log.error("tFileInputJSON_1 - " + e_tFileInputJSON_1.getMessage());
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
System.err.println(e_tFileInputJSON_1.getMessage());
}
com.jayway.jsonpath.ReadContext document_tFileInputJSON_1 = null;
try {
if (filenameOrStream_tFileInputJSON_1 instanceof java.io.InputStream) {
is_tFileInputJSON_1 = (java.io.InputStream) filenameOrStream_tFileInputJSON_1;
} else {
is_tFileInputJSON_1 = new java.io.FileInputStream((String) filenameOrStream_tFileInputJSON_1);
}
document_tFileInputJSON_1 = parseContext_tFileInputJSON_1.parse(is_tFileInputJSON_1, "UTF-8");
com.jayway.jsonpath.JsonPath compiledLoopPath_tFileInputJSON_1 = jsonPathCache_tFileInputJSON_1
.getCompiledJsonPath(loopPath_tFileInputJSON_1);
Object result_tFileInputJSON_1 = document_tFileInputJSON_1.read(compiledLoopPath_tFileInputJSON_1,
net.minidev.json.JSONObject.class);
if (result_tFileInputJSON_1 instanceof net.minidev.json.JSONArray) {
resultset_tFileInputJSON_1 = (net.minidev.json.JSONArray) result_tFileInputJSON_1;
} else {
resultset_tFileInputJSON_1.add(result_tFileInputJSON_1);
}
} catch (java.lang.Exception e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
log.error("tFileInputJSON_1 - " + e_tFileInputJSON_1.getMessage());
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
System.err.println(e_tFileInputJSON_1.getMessage());
} finally {
if (is_tFileInputJSON_1 != null) {
is_tFileInputJSON_1.close();
}
}
String jsonPath_tFileInputJSON_1 = null;
com.jayway.jsonpath.JsonPath compiledJsonPath_tFileInputJSON_1 = null;
Object value_tFileInputJSON_1 = null;
log.info("tFileInputJSON_1 - Retrieving records from data.");
Object root_tFileInputJSON_1 = null;
for (Object row_tFileInputJSON_1 : resultset_tFileInputJSON_1) {
nb_line_tFileInputJSON_1++;
log.debug("tFileInputJSON_1 - Retrieving the record " + (nb_line_tFileInputJSON_1) + ".");
row1 = null;
boolean whetherReject_tFileInputJSON_1 = false;
row1 = new row1Struct();
try {
jsonPath_tFileInputJSON_1 = "$.Nom";
compiledJsonPath_tFileInputJSON_1 = jsonPathCache_tFileInputJSON_1
.getCompiledJsonPath(jsonPath_tFileInputJSON_1);
try {
value_tFileInputJSON_1 = compiledJsonPath_tFileInputJSON_1.read(row_tFileInputJSON_1);
row1.Nom = value_tFileInputJSON_1 == null ?
null : value_tFileInputJSON_1.toString();
} catch (com.jayway.jsonpath.PathNotFoundException e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
row1.Nom =
null;
}
jsonPath_tFileInputJSON_1 = "$.Prenom";
compiledJsonPath_tFileInputJSON_1 = jsonPathCache_tFileInputJSON_1
.getCompiledJsonPath(jsonPath_tFileInputJSON_1);
try {
value_tFileInputJSON_1 = compiledJsonPath_tFileInputJSON_1.read(row_tFileInputJSON_1);
row1.Prenom = value_tFileInputJSON_1 == null ?
null : value_tFileInputJSON_1.toString();
} catch (com.jayway.jsonpath.PathNotFoundException e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
row1.Prenom =
null;
}
jsonPath_tFileInputJSON_1 = "$.Num_Telephone";
compiledJsonPath_tFileInputJSON_1 = jsonPathCache_tFileInputJSON_1
.getCompiledJsonPath(jsonPath_tFileInputJSON_1);
try {
value_tFileInputJSON_1 = compiledJsonPath_tFileInputJSON_1.read(row_tFileInputJSON_1);
row1.Num_Telephone = value_tFileInputJSON_1 == null ?
null : value_tFileInputJSON_1.toString();
} catch (com.jayway.jsonpath.PathNotFoundException e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
row1.Num_Telephone =
null;
}
} catch (java.lang.Exception e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
whetherReject_tFileInputJSON_1 = true;
log.error("tFileInputJSON_1 - " + e_tFileInputJSON_1.getMessage());
System.err.println(e_tFileInputJSON_1.getMessage());
row1 = null;
globalMap.put("tFileInputJSON_1_ERROR_MESSAGE", e_tFileInputJSON_1.getMessage());
}
currentComponent = "tFileInputJSON_1";
tos_count_tFileInputJSON_1++;
currentComponent = "tFileInputJSON_1";
if (row1 != null) {
currentComponent = "tMap_1";
if (runStat.update(execStat, enableLogStash, iterateId, 1, 1
, "row1", "tFileInputJSON_1", "tFileInputJSON_1", "tFileInputJSON", "tMap_1", "tMap_1",
"tMap"
)) {
talendJobLogProcess(globalMap);
}
if (log.isTraceEnabled()) {
log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
}
boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
boolean rejectedInnerJoin_tMap_1 = false;
boolean mainRowRejected_tMap_1 = false;
{
Var__tMap_1__Struct Var = Var__tMap_1;
OUT = null;
count_OUT_tMap_1++;
OUT_tmp.Nom = row1.Nom;
OUT_tmp.Prenom = row1.Prenom;
OUT_tmp.Num_Telephone = row1.Num_Telephone;
OUT = OUT_tmp;
log.debug("tMap_1 - Outputting the record " + count_OUT_tMap_1
+ " of the output table 'OUT'.");
}
rejectedInnerJoin_tMap_1 = false;
tos_count_tMap_1++;
currentComponent = "tMap_1";
if (OUT != null) {
currentComponent = "tFileOutputJSON_1";
if (runStat.update(execStat, enableLogStash, iterateId, 1, 1
, "OUT", "tMap_1", "tMap_1", "tMap", "tFileOutputJSON_1", "tFileOutputJSON_1",
"tFileOutputJSON"
)) {
talendJobLogProcess(globalMap);
}
if (log.isTraceEnabled()) {
log.trace("OUT - " + (OUT == null ? "" : OUT.toLogString()));
}
org.json.simple.JSONObject jsonRowtFileOutputJSON_1 = new org.json.simple.JSONObject();
if (OUT.Nom != null) {
jsonRowtFileOutputJSON_1.put("Nom", OUT.Nom);
} else {
jsonRowtFileOutputJSON_1.put("Nom", null);
}
if (OUT.Prenom != null) {
jsonRowtFileOutputJSON_1.put("Prenom", OUT.Prenom);
} else {
jsonRowtFileOutputJSON_1.put("Prenom", null);
}
if (OUT.Num_Telephone != null) {
jsonRowtFileOutputJSON_1.put("Num_Telephone", OUT.Num_Telephone);
} else {
jsonRowtFileOutputJSON_1.put("Num_Telephone", null);
}
if (!isFirst_tFileOutputJSON_1) {
outtFileOutputJSON_1.append(",");
}
isFirst_tFileOutputJSON_1 = false;
outtFileOutputJSON_1.append(jsonRowtFileOutputJSON_1.toJSONString());
nb_line_tFileOutputJSON_1++;
log.debug("tFileOutputJSON_1 - Writing the record " + nb_line_tFileOutputJSON_1 + ".");
tos_count_tFileOutputJSON_1++;
currentComponent = "tFileOutputJSON_1";
currentComponent = "tFileOutputJSON_1";
}
currentComponent = "tMap_1";
}
currentComponent = "tFileInputJSON_1";
currentComponent = "tFileInputJSON_1";
}
globalMap.put("tFileInputJSON_1_NB_LINE", nb_line_tFileInputJSON_1);
log.debug("tFileInputJSON_1 - Retrieved records count: " + nb_line_tFileInputJSON_1 + " .");
if (log.isDebugEnabled())
log.debug("tFileInputJSON_1 - " + ("Done."));
ok_Hash.put("tFileInputJSON_1", true);
end_Hash.put("tFileInputJSON_1", System.currentTimeMillis());
currentComponent = "tMap_1";
log.debug("tMap_1 - Written records count in the table 'OUT': " + count_OUT_tMap_1 + ".");
if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
"tFileInputJSON_1", "tFileInputJSON_1", "tFileInputJSON", "tMap_1", "tMap_1", "tMap",
"output")) {
talendJobLogProcess(globalMap);
}
if (log.isDebugEnabled())
log.debug("tMap_1 - " + ("Done."));
ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tFileOutputJSON_1";
outtFileOutputJSON_1.print("]}");
outtFileOutputJSON_1.close();
globalMap.put("tFileOutputJSON_1_NB_LINE", nb_line_tFileOutputJSON_1);
log.debug("tFileOutputJSON_1 - Written records count: " + nb_line_tFileOutputJSON_1 + " .");
if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "OUT", 2, 0, "tMap_1",
"tMap_1", "tMap", "tFileOutputJSON_1", "tFileOutputJSON_1", "tFileOutputJSON", "output")) {
talendJobLogProcess(globalMap);
}
if (log.isDebugEnabled())
log.debug("tFileOutputJSON_1 - " + ("Done."));
ok_Hash.put("tFileOutputJSON_1", true);
end_Hash.put("tFileOutputJSON_1", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
if (!(e instanceof TalendException)) {
log.fatal(currentComponent + " " + e.getMessage(), e);
}
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
try {
currentComponent = "tFileInputJSON_1";
currentComponent = "tMap_1";
currentComponent = "tFileOutputJSON_1";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tFileInputJSON_1_SUBPROCESS_STATE", 1);
}
public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);
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
ok_Hash.put("talendJobLog", false);
start_Hash.put("talendJobLog", System.currentTimeMillis());
currentComponent = "talendJobLog";
int tos_count_talendJobLog = 0;
for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
.custom("father_pid", fatherPid).custom("root_pid", rootPid);
org.talend.logging.audit.Context log_context_talendJobLog = null;
if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
.rows(jcm.row_count).duration(duration).build();
auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
.status(jcm.status).build();
auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
.connectorType(jcm.component_name).connectorId(jcm.component_id)
.connectorLabel(jcm.component_label).build();
auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
.rows(jcm.total_row_number).duration(duration).build();
auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
.rows(jcm.total_row_number).duration(duration).build();
auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
}
}
currentComponent = "talendJobLog";
tos_count_talendJobLog++;
currentComponent = "talendJobLog";
currentComponent = "talendJobLog";
currentComponent = "talendJobLog";
ok_Hash.put("talendJobLog", true);
end_Hash.put("talendJobLog", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
if (!(e instanceof TalendException)) {
log.fatal(currentComponent + " " + e.getMessage(), e);
}
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
try {
currentComponent = "talendJobLog";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
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
final DFM_Chargement_Contacts_JSON DFM_Chargement_Contacts_JSONClass = new DFM_Chargement_Contacts_JSON();
int exitCode = DFM_Chargement_Contacts_JSONClass.runJobInTOS(args);
if (exitCode == 0) {
log.info("TalendJob: 'DFM_Chargement_Contacts_JSON' - Done.");
}
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
if (!"".equals(log4jLevel)) {
if ("trace".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.TRACE);
} else if ("debug".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.DEBUG);
} else if ("info".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.INFO);
} else if ("warn".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.WARN);
} else if ("error".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.ERROR);
} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.FATAL);
} else if ("off".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.OFF);
}
org.apache.logging.log4j.core.config.Configurator
.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());
}
log.info("TalendJob: 'DFM_Chargement_Contacts_JSON' - Start.");
if (enableLogStash) {
java.util.Properties properties_talendJobLog = new java.util.Properties();
properties_talendJobLog.setProperty("root.logger", "audit");
properties_talendJobLog.setProperty("encoding", "UTF-8");
properties_talendJobLog.setProperty("application.name", "Talend Studio");
properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
properties_talendJobLog.setProperty("log.appender", "file");
properties_talendJobLog.setProperty("appender.file.path", "audit.json");
properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
properties_talendJobLog.setProperty("host", "false");
System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
System.getProperty(key)));
org.apache.logging.log4j.core.config.Configurator
.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);
auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
.createJobAuditLogger(properties_talendJobLog);
}
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
if (portStats != null) {
if (portStats < 0 || portStats > 65535) {
System.err.println("The statistics socket port " + portStats + " is invalid.");
execStat = false;
}
} else {
execStat = false;
}
boolean inOSGi = routines.system.BundleUtils.inOSGi();
try {
java.util.Dictionary<String, Object> jobProperties = null;
if (inOSGi) {
jobProperties = routines.system.BundleUtils.getJobProperties(jobName);
if (jobProperties != null && jobProperties.get("context") != null) {
contextStr = (String) jobProperties.get("context");
}
}
java.io.InputStream inContext = DFM_Chargement_Contacts_JSON.class.getClassLoader()
.getResourceAsStream("dfm/dfm_chargement_contacts_json_0_1/contexts/" + contextStr + ".properties");
if (inContext == null) {
inContext = DFM_Chargement_Contacts_JSON.class.getClassLoader()
.getResourceAsStream("config/contexts/" + contextStr + ".properties");
}
if (inContext != null) {
try {
if (context != null && context.isEmpty()) {
defaultProps.load(inContext);
if (inOSGi && jobProperties != null) {
java.util.Enumeration<String> keys = jobProperties.keys();
while (keys.hasMoreElements()) {
String propKey = keys.nextElement();
if (defaultProps.containsKey(propKey)) {
defaultProps.put(propKey, (String) jobProperties.get(propKey));
}
}
}
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
"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));
if (execStat) {
try {
runStat.openSocket(!isChildJob);
runStat.setAllPID(rootPid, fatherPid, pid, jobName);
runStat.startThreadStat(clientHost, portStats);
runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
} catch (java.io.IOException ioException) {
ioException.printStackTrace();
}
}
java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
globalMap.put("concurrentHashMap", concurrentHashMap);
long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
long endUsedMemory = 0;
long end = 0;
startTime = System.currentTimeMillis();
this.globalResumeTicket = true;
if (enableLogStash) {
talendJobLog.addJobStartMessage();
try {
talendJobLogProcess(globalMap);
} catch (java.lang.Exception e) {
e.printStackTrace();
}
}
this.globalResumeTicket = false;
try {
errorCode = null;
tFileInputJSON_1Process(globalMap);
if (!"failure".equals(status)) {
status = "end";
}
} catch (TalendException e_tFileInputJSON_1) {
globalMap.put("tFileInputJSON_1_SUBPROCESS_STATE", -1);
e_tFileInputJSON_1.printStackTrace();
}
this.globalResumeTicket = true;
end = System.currentTimeMillis();
if (watch) {
System.out.println((end - startTime) + " milliseconds");
}
endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
if (false) {
System.out.println((endUsedMemory - startUsedMemory)
+ " bytes memory increase when running : DFM_Chargement_Contacts_JSON");
}
if (enableLogStash) {
talendJobLog.addJobEndMessage(startTime, end, status);
try {
talendJobLogProcess(globalMap);
} catch (java.lang.Exception e) {
e.printStackTrace();
}
}
if (execStat) {
runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
runStat.stopThreadStat();
}
int returnCode = 0;
if (errorCode == null) {
returnCode = status != null && status.equals("failure") ? 1 : 0;
} else {
returnCode = errorCode.intValue();
}
resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
"" + returnCode, "", "", "");
resumeUtil.flush();
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