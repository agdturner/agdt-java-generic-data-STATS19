/*
 * Copyright 2018 geoagdt.
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
package uk.ac.leeds.ccg.andyt.generic.data.stats19.core;

import uk.ac.leeds.ccg.andyt.generic.memory.Generic_MemoryManager;
import uk.ac.leeds.ccg.andyt.generic.memory.Generic_Memory;

/**
 *
 * @author Andy Turner
 */
public abstract class STATS19_MemoryManager
        extends Generic_MemoryManager
        implements Generic_Memory {

    //static final long serialVersionUID = 1L;
    //public static long Memory_Threshold = 3000000000L;
    public static long Memory_Threshold = 2000000000L;

}