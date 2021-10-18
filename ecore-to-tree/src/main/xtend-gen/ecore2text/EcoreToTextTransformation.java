package ecore2text;

import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class EcoreToTextTransformation {
  /**
   * Generate a tree representation of a Ecore metamodel.
   * The tree is generated by considering:
   * 		- Classifiers (EClass)
   * 		- Attributes (EAttribute)
   * 		- Associations/Containments (EReference)
   * Outputs the tree in a json format.
   * 
   * @param  filePath 	an absolute path giving the base location of the Ecore metamodel
   * @return String		the json representation of the Ecore metamodel
   */
  public String generate(final String filePath) {
    final ResourceSetImpl resourceSet = new ResourceSetImpl();
    Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    EcoreResourceFactoryImpl _ecoreResourceFactoryImpl = new EcoreResourceFactoryImpl();
    _extensionToFactoryMap.put(
      "ecore", _ecoreResourceFactoryImpl);
    final Resource resource = resourceSet.getResource(
      URI.createFileURI(filePath), true);
    EObject _get = resource.getContents().get(0);
    final EPackage model = ((EPackage) _get);
    return this.generateCode(model);
  }
  
  public String generateCode(final EPackage model) {
    String _xblockexpression = null;
    {
      EList<EClassifier> classifiers = model.getEClassifiers();
      final Function1<EClassifier, Boolean> _function = (EClassifier e) -> {
        return Boolean.valueOf((e instanceof EClassImpl));
      };
      final Function1<EClassifier, EClassImpl> _function_1 = (EClassifier e) -> {
        return ((EClassImpl) e);
      };
      Iterable<EClassImpl> cls = IterableExtensions.<EClassifier, EClassImpl>map(IterableExtensions.<EClassifier>filter(classifiers.stream().collect(Collectors.<EClassifier>toList()), _function), _function_1);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("{");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("\"root\": \"<MODEL>\",");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("\"children\": [");
      _builder.newLine();
      {
        boolean _hasElements = false;
        for(final EClassImpl classifier : cls) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate(",", "\t\t");
          }
          _builder.append("\t\t");
          _builder.append("{");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("\"root\": \"<CLS>\",");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("\"name\": \"");
          String _name = classifier.getName();
          _builder.append(_name, "\t\t\t");
          _builder.append("\",");
          _builder.newLineIfNotEmpty();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("\"attrs\": [");
          _builder.newLine();
          {
            EList<EAttribute> _eAttributes = classifier.getEAttributes();
            boolean _hasElements_1 = false;
            for(final EAttribute member : _eAttributes) {
              if (!_hasElements_1) {
                _hasElements_1 = true;
              } else {
                _builder.appendImmediate(",", "\t\t\t\t");
              }
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("{");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("\t");
              EClassifier eAttr = member.getEType();
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("\t");
              InternalEObject eAttrData = ((InternalEObject) eAttr);
              _builder.newLineIfNotEmpty();
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("\t");
              URI _eProxyURI = eAttrData.eProxyURI();
              URI eAttrDataProxy = ((URI) _eProxyURI);
              _builder.newLineIfNotEmpty();
              {
                if (((eAttrDataProxy != null) && (eAttr.getName() == null))) {
                  _builder.append("\t\t");
                  _builder.append("\t\t");
                  _builder.append("\t");
                  _builder.append("\"");
                  String _replace = eAttrDataProxy.fragment().replace("/", "");
                  _builder.append(_replace, "\t\t\t\t\t");
                  _builder.append("\": \"");
                  String _name_1 = member.getName();
                  _builder.append(_name_1, "\t\t\t\t\t");
                  _builder.append("\"");
                  _builder.newLineIfNotEmpty();
                } else {
                  _builder.append("\t\t");
                  _builder.append("\t\t");
                  _builder.append("\t");
                  _builder.append("\"");
                  String _name_2 = eAttr.getName();
                  _builder.append(_name_2, "\t\t\t\t\t");
                  _builder.append("\": \"");
                  String _name_3 = member.getName();
                  _builder.append(_name_3, "\t\t\t\t\t");
                  _builder.append("\"");
                  _builder.newLineIfNotEmpty();
                }
              }
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("}");
              _builder.newLine();
            }
          }
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("],");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("\"assocs\": [");
          _builder.newLine();
          {
            EList<EReference> _eAllReferences = classifier.getEAllReferences();
            boolean _hasElements_2 = false;
            for(final EReference member_1 : _eAllReferences) {
              if (!_hasElements_2) {
                _hasElements_2 = true;
              } else {
                _builder.appendImmediate(",", "\t\t\t\t");
              }
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("{");
              _builder.newLine();
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("\t");
              String eRefName = member_1.getEReferenceType().getName();
              _builder.newLineIfNotEmpty();
              {
                if ((eRefName != null)) {
                  _builder.append("\t\t");
                  _builder.append("\t\t");
                  _builder.append("\t");
                  _builder.append("\"");
                  _builder.append(eRefName, "\t\t\t\t\t");
                  _builder.append("\": \"");
                  String _name_4 = member_1.getName();
                  _builder.append(_name_4, "\t\t\t\t\t");
                  _builder.append("\"");
                  _builder.newLineIfNotEmpty();
                }
              }
              _builder.append("\t\t");
              _builder.append("\t\t");
              _builder.append("}");
              _builder.newLine();
            }
          }
          _builder.append("\t\t");
          _builder.append("\t");
          _builder.append("]");
          _builder.newLine();
          _builder.append("\t\t");
          _builder.append("}");
          _builder.newLine();
        }
      }
      _builder.append("\t");
      _builder.append("]");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
}
