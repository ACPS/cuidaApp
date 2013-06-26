package android.bitmapfun.provider;

import java.util.LinkedList;
import java.util.List;

/*
 *
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



/**
 * Some simple test data to use for this sample app.
 */
public class Images {

    /**
     * This are PicasaWeb URLs and could potentially change. Ideally the PicasaWeb API should be
     * used to fetch the URLs.
     *
     * Credit to Romain Guy for the photos:
     * http://www.curious-creature.org/
     * https://plus.google.com/109538161516040592207/about
     * http://www.flickr.com/photos/romainguy
     */
//    public final static String[] imageUrls = new String[] {
//            "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg",
//            "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
//            "https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s1024/Another%252520Rockaway%252520Sunset.jpg",
//            "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s1024/Antelope%252520Butte.jpg",
//           
//    };
//
//    /**
//     * This are PicasaWeb thumbnail URLs and could potentially change. Ideally the PicasaWeb API
//     * should be used to fetch the URLs.
//     *
//     * Credit to Romain Guy for the photos:
//     * http://www.curious-creature.org/
//     * https://plus.google.com/109538161516040592207/about
//     * http://www.flickr.com/photos/romainguy
//     */
//    public final static String[] imageThumbUrls = new String[] {
//            "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s160-c/A%252520Photographer.jpg",
//            "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s160-c/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg",
//            "https://lh5.googleusercontent.com/-7qZeDtRKFKc/URquWZT1gOI/AAAAAAAAAbs/hqWgteyNXsg/s160-c/Another%252520Rockaway%252520Sunset.jpg",
//            "https://lh3.googleusercontent.com/--L0Km39l5J8/URquXHGcdNI/AAAAAAAAAbs/3ZrSJNrSomQ/s160-c/Antelope%252520Butte.jpg",
//    };
//	
	 public final static List<Categoria> imageUrls = new LinkedList<Categoria>(); 
	 public final static List<Categoria> imageThumbUrls = new LinkedList<Categoria>(); 
	 
	
	 public static void addCategory(Categoria category){
		 imageUrls.add(category);
		 imageThumbUrls.add(category);
	 }
	 
		
}
