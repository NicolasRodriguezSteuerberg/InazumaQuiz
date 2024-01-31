#### Cambiar el icono y nombre de la aplicación
1. Cambiar el icono de la aplicación: 
    - Añadir el icono
      - File > New > Image Asset
      - Añadir tu icono
    - Cambiar el icono
      - Abrir el archivo `android/app/src/main/AndroidManifest.xml`
      - Buscar la etiqueta `<application` y el atributo `android:icon=` y cambiar el valor de `@mipmap/ic_launcher` por el nuevo icono
      - Buscar la etiqueta `<application` y el atributo `android:roundIcon=` y cambiar el valor de `@mipmap/ic_launcher_round` por el nuevo icono
      
2. Cambiar el nombre de la aplicación:
   - En el mismo archivo de antes: `android/app/src/main/AndroidManifest.xml`
   - Buscar la etiqueta `<application` y el atributo `android:label=` y darle control + click para ir al archivo `android/app/src/main/res/values/strings.xml`
   - Cambiar el valor de la etiqueta `<string name="app_name">` por el nuevo nombre de la aplicación