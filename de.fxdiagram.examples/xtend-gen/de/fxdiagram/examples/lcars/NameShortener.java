package de.fxdiagram.examples.lcars;

import com.google.common.base.Objects;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class NameShortener {
  private final Pattern initialsPattern = Pattern.compile("((^|\\s)[A-Z]\\.\\s*)");
  
  private final Pattern parenthesesPattern = Pattern.compile("\\s*\\([^\\)]+\\)");
  
  public String shortenName(final String name) {
    Matcher matcher = this.initialsPattern.matcher(name);
    boolean _find = matcher.find();
    if (_find) {
      return matcher.replaceAll(" ");
    }
    Matcher _matcher = this.parenthesesPattern.matcher(name);
    matcher = _matcher;
    boolean _find_1 = matcher.find();
    if (_find_1) {
      return matcher.replaceAll("()");
    }
    final String[] subnames = name.split(" ");
    int _size = ((List<String>)Conversions.doWrapArray(subnames)).size();
    final int _switchValue = _size;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_switchValue,1)) {
        _matched=true;
        String _head = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(subnames)));
        String _substring = _head.substring(0, 8);
        return (_substring + "...");
      }
    }
    if (!_matched) {
      if (Objects.equal(_switchValue,2)) {
        _matched=true;
        final String firstName = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(subnames)));
        int _length = firstName.length();
        boolean _lessEqualsThan = (_length <= 2);
        if (_lessEqualsThan) {
          return IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(subnames)));
        } else {
          char _charAt = firstName.charAt(0);
          String _plus = (Character.valueOf(_charAt) + ". ");
          String _last = IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(subnames)));
          return (_plus + _last);
        }
      }
    }
    String _head_1 = IterableExtensions.<String>head(((Iterable<String>)Conversions.doWrapArray(subnames)));
    String _plus_1 = (_head_1 + " ");
    String _last_1 = IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(subnames)));
    return (_plus_1 + _last_1);
  }
}
