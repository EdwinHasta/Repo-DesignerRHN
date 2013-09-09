This is part of OpenFaces library for JSF distribution.

  I. Contents
 II. Runtime Files
III. Installation Instructions
 IV. Support
  V. Third-Party Libraries


I. Contents

The distribution contains:
readme.txt - this file.
openfaces.jar - the OpenFaces library.
license\ - the License Agreement for the OpenFaces library and third-party product licenses.
lib\ - the third-party libraries required for the OpenFaces library.
doc\ - the OpenFaces library documentation. See index.html as a starting point.


II. Runtime Files

Redistributable (run-time) files include:
* openfaces.jar file
* libraries residing in the lib\ folder 


III. Installation Instructions

Provided that you already have a JSF application, the following steps let you to use the OpenFaces library in your Web application:

1. Add openfaces.jar to the application libraries.

2. Add the following libraries found in the distribution under the lib\ folder to the application:
* cssparser.jar - Unmodified library, version 0.9.5. You can skip adding it if it's already available for the application.
* sac.jar - Unmodified library, version 1.3. You can skip adding it if it's already available for the application.
* jcommon.jar - Unmodified library, version 1.0.16. You can skip adding it if it's already available for the application.
* jfreechart.jar - Unmodified library, version 1.0.13. You can skip adding it if it's already available for the application.
* commons-collections.jar - Unmodified library, version 3.1. You can skip adding it if it's already available for the application.	 

3. (Optional) If you want to turn on automatic client-side validation for all of your application pages, add the following lines to the WEB-INF/web.xml file (see Validation Framework documentation for more information):

  <context-param>
    <param-name>org.openfaces.validation.clientValidation</param-name>
    <param-value>onSubmit</param-value>
  </context-param>

NOTE: OpenFaces requires Mojarra version 2.0.3 or later. This is required to overcome Ajax issues that might appear in
some cases when using earlier versions of Mojarra.

IV. Support

Visit support page for the OpenFaces library at http://openfaces.org/support/


V. Third-Party Libraries

The OpenFaces library uses:
* The software developed by the JDOM Project (http://www.jdom.org/)
* The JFreeChart library (http://www.jfree.org/jfreechart/) that is covered by the LGPL.
* The JCommon class library (http://www.jfree.org/jcommon/) that is covered by the LGPL.
* The CSS Parser library (http://cssparser.sourceforge.net/) that is covered by the LGPL.
* Parts of code from Apache MyFaces Project (http://myfaces.apache.org/) that is licensed under the Apache License, Version 2.0
* Parts of code from JSON software (http://www.json.org/)
* Parts of code from Apache Commons codec (http://jakarta.apache.org/commons/codec/) that is licensed under the Apache License, Version 2.0.
