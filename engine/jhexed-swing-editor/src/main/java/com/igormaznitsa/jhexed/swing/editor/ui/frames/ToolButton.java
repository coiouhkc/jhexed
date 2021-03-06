/* 
 * Copyright 2014 Igor Maznitsa (http://www.igormaznitsa.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.jhexed.swing.editor.ui.frames;

import com.igormaznitsa.jhexed.swing.editor.model.ToolType;
import java.awt.Insets;
import javax.swing.JToggleButton;

public class ToolButton extends JToggleButton {
  private static final long serialVersionUID = -4462439797288345867L;
  private final ToolType type;
  
  public ToolButton(final ToolType type){
    super();
    this.type = type;
    this.setMargin(new Insets(5, 5, 5, 5));
  }
  
  public ToolType getType(){
    return this.type;
  }
}
